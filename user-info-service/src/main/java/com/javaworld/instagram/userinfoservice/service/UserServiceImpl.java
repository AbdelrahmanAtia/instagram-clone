package com.javaworld.instagram.userinfoservice.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;
import com.javaworld.instagram.userinfoservice.service.dto.PostsCountResponse;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private static final String POST_SERVICE_URL = "http://post";
	private static final String POST_SERVICE_CONTEXT = "insta-post-service";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	private final WebClient webClient;

	@Autowired
	public UserServiceImpl(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
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

		String url = POST_SERVICE_URL + POST_SERVICE_CONTEXT + "/postscount?userUUID=" + userUuid;

		UserEntity existingUser = userRepository.findByUserUuid(userUuid).orElseThrow(() -> {
			throw new RuntimeException("user with uuid: " + userUuid.toString() + " not found");
		});
		
		User userDto = userMapper.mapUserEntityToDto(existingUser);
		
		boolean getPostsCountFromPostsService = true; // TODO: shall be an external configuration flag

		if (getPostsCountFromPostsService) {
			PostsCountResponse postsCountResponse = webClient.get().uri(url).retrieve()
					.bodyToMono(PostsCountResponse.class).block();
			// .log(LOG.getName(), FINE)
			// .onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
			
			userDto.setPostsCount(postsCountResponse.getPostsCount());
		}

		return userDto;
	}

}
