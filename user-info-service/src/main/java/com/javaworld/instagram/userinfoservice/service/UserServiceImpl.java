package com.javaworld.instagram.userinfoservice.service;

import static java.util.logging.Level.FINE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.integration.UserInfoIntegration;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;
import com.javaworld.instagram.userinfoservice.service.dto.ProfileDetails;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserInfoIntegration integration;
	
	@Override
	public Mono<UserEntity> createUser(UserEntity userEntity) {

		Mono<UserEntity> newEntity = userRepository.save(userEntity)
				.log(LOG.getName(), FINE)
				.onErrorMap(
						DuplicateKeyException.class,
						ex -> new InvalidInputException("Duplicate key, UserName: " + userEntity.getUsername()));

		return newEntity;
	}
	
	@Override
	public Mono<ProfileDetails> getProfileDetails(String userName) {

		int userId = 0; // TODO: load user by user name then retrieve the user id

		LOG.info("Will get profile details for user.id={}", userId);

		//retrieve each part of the profile details in a parallel way
		return Mono
				.zip(values -> createProfileDetails((Integer) values[0], (Integer) values[1], (Integer) values[2]),
						integration.getUserPostsCount(userId), getFollowersCount(), getFollowingCount())
				.doOnError(ex -> LOG.warn("createProfileDetails failed: {}", ex.toString()))
				.log(LOG.getName(), FINE);
		
	}

	private ProfileDetails createProfileDetails(int postsCount, int followersCount, int followingCount) {

		ProfileDetails profileDetails = new ProfileDetails();
		profileDetails.setPostsCount(postsCount);
		profileDetails.setFollowersCount(followersCount);
		profileDetails.setFollowingCount(followingCount);

		return profileDetails;
	}

	private Mono<Integer> getFollowersCount() {

		//TODO: to be implemented
		return Mono.empty();
	}

	private Mono<Integer> getFollowingCount() {

		//TODO: to be implemented
		return Mono.empty();
	}

}
