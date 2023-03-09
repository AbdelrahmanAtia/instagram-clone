package com.javaworld.instagram.userinfoservice.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.javaworld.instagram.userinfoservice.commons.exceptions.HttpErrorInfo;
import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.commons.exceptions.NotFoundException;
import com.javaworld.instagram.userinfoservice.configuration.PropertiesConfig;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;
import com.javaworld.instagram.userinfoservice.service.dto.PostsCountResponse;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private PropertiesConfig propertiesConfig;

	private final WebClient webClient;
	
	private final ObjectMapper mapper;

	@Autowired
	public UserServiceImpl(WebClient.Builder webClientBuilder, ObjectMapper mapper) {
		this.webClient = webClientBuilder.build();
	    this.mapper = mapper;
	}

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

	public User findUser(UUID userUuid) {

		String url = propertiesConfig.getVirtualPostServiceUrl() + propertiesConfig.getServicesContext()
				+ "/posts/count?userUuid=" + userUuid;
		
	    logger.info("Will call the findPostsCount API on URL: {}", url);
		
		UserEntity existingUser = userRepository.findByUserUuid(userUuid).orElseThrow(() -> {
			throw new RuntimeException("user with uuid: " + userUuid.toString() + " not found");
		});
		
		User userDto = userMapper.mapUserEntityToDto(existingUser);
		
		boolean getPostsCountFromPostsService = true; // TODO: shall be an external configuration flag
		
		if (getPostsCountFromPostsService) {
			PostsCountResponse postsCountResponse = webClient.get()
					.uri(url)
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.bodyToMono(PostsCountResponse.class)
					// .log(LOG.getName(), FINE)
					.onErrorMap(WebClientResponseException.class, ex -> handleException(ex)).block();

			userDto.setPostsCount(postsCountResponse.getPostsCount());
		}
		
		return userDto;
	}
	
	private Throwable handleException(Throwable ex) {

		logger.info("starting handleException()..");
		
		if (!(ex instanceof WebClientResponseException)) {
			logger.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
			return ex;
		}

		WebClientResponseException wcre = (WebClientResponseException) ex;

		switch (wcre.getStatusCode()) {

		case NOT_FOUND:
			return new NotFoundException(getErrorMessage(wcre));

		case UNPROCESSABLE_ENTITY:
			return new InvalidInputException(getErrorMessage(wcre));

		default:
			logger.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
			logger.warn("Error body: {}", wcre.getResponseBodyAsString());
			return ex;
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
