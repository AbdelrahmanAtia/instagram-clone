package com.javaworld.instagram.userinfoservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;

import reactor.core.publisher.Mono;
import static java.util.logging.Level.FINE;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;

	@Override
	public Mono<UserEntity> createUser(UserEntity userEntity) {


		Mono<UserEntity> newEntity = userRepository.save(userEntity)
				.log(LOG.getName(), FINE)
				.onErrorMap(
						DuplicateKeyException.class,
						ex -> new InvalidInputException("Duplicate key, UserName: " + userEntity.getUsername()));

		return newEntity;
	}

}
