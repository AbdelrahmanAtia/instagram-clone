package com.javaworld.instagram.userinfoservice.service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.configuration.PropertiesConfig;
import com.javaworld.instagram.userinfoservice.integration.PostServiceIntegration;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	private PostServiceIntegration postServiceIntegration;

	@Override
	public User createUser(User user) {

		UserEntity userEntity = userMapper.dtoToEntity(user);
		try {
			UserEntity savedUser = userRepository.save(userEntity);
			logger.info("created user with name: " + savedUser.getUsername());

			return userMapper.mapUserEntityToDto(userEntity);

		} catch (DuplicateKeyException ex) {
			throw new InvalidInputException("Duplicate key, UserName: " + userEntity.getUsername());
		}

	}

	//TODO: delay & faultPercent shall be removed, this is only for testing the circuit breaker
	public User findUser(UUID userUuid, int delay, int faultPercent) {

		String url = propertiesConfig.getVirtualPostServiceUrl() + propertiesConfig.getServicesContext()
				+ "/posts/count?userUuid=" + userUuid;

		logger.info("Will call the findPostsCount API on URL: {}", url);

		UserEntity existingUser = userRepository.findByUserUuid(userUuid).orElseThrow(() -> {
			throw new RuntimeException("user with uuid: " + userUuid.toString() + " not found");
		});

		User userDto = userMapper.mapUserEntityToDto(existingUser);

		boolean getPostsCountFromPostsService = true; // TODO: shall be an external configuration flag

		if (getPostsCountFromPostsService) {
			
			try {
				int postsCount = postServiceIntegration.getPostsCountByUserUuid(userUuid, delay, faultPercent).get();
				userDto.setPostsCount(postsCount);
			} catch (InterruptedException | ExecutionException ex) {
				throw new RuntimeException(ex.getMessage());
			}
			
		}

		return userDto;
	}	

}
