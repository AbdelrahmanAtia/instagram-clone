package com.javaworld.instagram.userinfoservice.service;

import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaworld.instagram.userinfoservice.commons.exceptions.HttpErrorInfo;
import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.commons.exceptions.NotFoundException;
import com.javaworld.instagram.userinfoservice.integration.PostServiceIntegration;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private final ObjectMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;
		
	@Autowired
	private PostServiceIntegration postServiceIntegration;
	
	
	public UserServiceImpl(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public User createUser(User user) {

		UserEntity userEntity = userMapper.dtoToEntity(user);
		try {
			//TODO: make username unique
			UserEntity savedUser = userRepository.save(userEntity);
			logger.info("created user with name: " + savedUser.getUsername());
			
			return userMapper.mapUserEntityToDto(userEntity);

		} catch (DuplicateKeyException ex) {
			throw new InvalidInputException("Duplicate key, UserName: " + userEntity.getUsername());
		}

	}

	//TODO: delay & faultPercent shall be removed, this is only for testing the circuit breaker
	public User findUser(UUID userUuid, int delay, int faultPercent) {

		UserEntity existingUser = userRepository.findByUserUuid(userUuid).orElseThrow(() -> {
			throw new NotFoundException("user with uuid: " + userUuid.toString() + " not found");
		});

		User userDto = userMapper.mapUserEntityToDto(existingUser);

		boolean getPostsCountFromPostsService = true; // TODO: shall be an external configuration flag

		if (getPostsCountFromPostsService) {
			
			try {
				int postsCount = postServiceIntegration.getPostsCountByUserUuid(userUuid, delay, faultPercent).block()
						.getPostsCount();
				userDto.setPostsCount(postsCount);

			}
			catch(RuntimeException ex) {
				handleException(ex);
			}
		}

		return userDto;
	}

	@Override
	@Transactional
	//TODO: enhance this service to make a soft delete and update the retrieval operations
	public int deleteUser(UUID userUuid) {
		logger.info("trying to delete user with uuid {}", userUuid);
		int deletedRowsCount = userRepository.deleteByUserUuid(userUuid);	
		
		if(deletedRowsCount > 0) {
			postServiceIntegration.deletePostsOfCurrentUser(userUuid);			
		}

		return deletedRowsCount;
	}	
	
	// TODO: move to a utility class
	private void handleException(RuntimeException ex)  {

		logger.info("starting handleException()..");

		if (!(ex instanceof WebClientResponseException)) {
			logger.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
			throw ex;
		}

		WebClientResponseException wcre = (WebClientResponseException) ex;

		switch (wcre.getStatusCode()) {

		case NOT_FOUND:
			throw new NotFoundException(getErrorMessage(wcre));

		case UNPROCESSABLE_ENTITY:
			throw new InvalidInputException(getErrorMessage(wcre));

		default:
			logger.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
			logger.warn("Error body: {}", wcre.getResponseBodyAsString());
			throw ex;
		}
	}

	private String getErrorMessage(WebClientResponseException ex) {
		try {
			return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
		} catch (IOException ioex) {
			return ex.getMessage();
		}
	}


}
