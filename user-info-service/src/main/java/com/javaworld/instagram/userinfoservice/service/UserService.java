package com.javaworld.instagram.userinfoservice.service;

import com.javaworld.instagram.userinfoservice.persistence.UserEntity;

import reactor.core.publisher.Mono;

public interface UserService {

	Mono<UserEntity> createUser(UserEntity userEntity);

}
