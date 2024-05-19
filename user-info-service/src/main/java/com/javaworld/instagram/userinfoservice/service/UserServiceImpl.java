package com.javaworld.instagram.userinfoservice.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaworld.instagram.userinfoservice.commons.exceptions.BadRequestException;
import com.javaworld.instagram.userinfoservice.commons.exceptions.HttpErrorInfo;
import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.commons.exceptions.NotFoundException;
import com.javaworld.instagram.userinfoservice.commons.utils.SecurityUtil;
import com.javaworld.instagram.userinfoservice.integration.PostServiceIntegration;
import com.javaworld.instagram.userinfoservice.persistence.FollowerEntity;
import com.javaworld.instagram.userinfoservice.persistence.FollowerRepository;
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
	private FollowerRepository followerRepository;
	
	@Autowired
	private UserMapper userMapper;
		
	@Autowired
	private PostServiceIntegration postServiceIntegration;
	
	
	public UserServiceImpl(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public User createUser(User user) {

		UserEntity userEntity = userMapper.toEntity(user);
		try {
			//TODO: make username unique
			UserEntity savedUser = userRepository.save(userEntity);
			logger.info("created user with name: " + savedUser.getUsername());
			
			return userMapper.toDto(userEntity);

		} catch (DuplicateKeyException ex) {
			throw new InvalidInputException("Duplicate key, UserName: " + userEntity.getUsername());
		}

	}

	//TODO: delay & faultPercent shall be removed, this is only for testing the circuit breaker
	public User findUser(UUID userUuid, int delay, int faultPercent) {

		UserEntity existingUser = userRepository.findByUserUuid(userUuid).orElseThrow(() -> {
			throw new NotFoundException("user with uuid: " + userUuid.toString() + " not found");
		});

		User userDto = userMapper.toDto(existingUser);

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
	public User partialUpdateUser(User updatedUserData) {

		UUID userUuid = updatedUserData.getUserUuid();
		UserEntity existingUser = userRepository.findByUserUuid(userUuid)
	            .orElseThrow(() -> new NotFoundException("User with uuid: " + userUuid.toString() + " not found"));

	    userMapper.partialUpdate(existingUser, updatedUserData);

	    UserEntity updatedUserEntity = userRepository.save(existingUser);
	    
	    return userMapper.toDto(updatedUserEntity);
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

	@Override
	public List<User> getSuggestedUsers(String size) {
		int count;
		if ("MIN".equalsIgnoreCase(size)) {
			count = 5; // Minimum number of suggested users
		} else if ("MAX".equalsIgnoreCase(size)) {
			count = 30; // Maximum number of suggested users
		} else {
			throw new InvalidInputException("Invalid value for size parameter. It should be either 'MIN' or 'MAX'.");
		}
		
		UUID currentUserUuid = SecurityUtil.getUserUuidFromAccessToken(getSecurityContext());
		List<UserEntity> suggestedUserEntities = userRepository.findSuggestedUsers(currentUserUuid, PageRequest.of(0, count));

		return userMapper.toDto(suggestedUserEntities);
	}

	
	@Override
	public void followUser(UUID followedId) {
		
		UUID currentUserUuid = SecurityUtil.getUserUuidFromAccessToken(getSecurityContext());
		
		logger.info("user with id {} is requesting to follow user with id {}", currentUserUuid, followedId);
		
		if(currentUserUuid.equals(followedId)) {
			throw new BadRequestException("user can't follow itself");
		}
		
		//loggedIn user
		UserEntity followerUserEntity = userRepository.findByUserUuid(currentUserUuid).orElseThrow(
				() -> new NotFoundException("logged in user with uuid: " + currentUserUuid.toString() + " not found"));

		UserEntity followedUser = userRepository.findByUserUuid(followedId).orElseThrow(
				() -> new NotFoundException("Followed User with uuid: " + followedId.toString() + " not found"));
		

		
		if (followerRepository.findByFollowerIdAndFollowedId(currentUserUuid, followedId).isPresent()) {
			logger.warn("user with id " + currentUserUuid + " already following user with id " + followedId);
			return;
		}
		
		FollowerEntity followerEntity = new FollowerEntity();
		followerEntity.setFollower(followerUserEntity);
		followerEntity.setFollowed(followedUser);		
		
		followerRepository.save(followerEntity);
		
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
	
	private SecurityContext getSecurityContext() {
		return SecurityContextHolder.getContext();
	}

}
