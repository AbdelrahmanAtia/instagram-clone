package com.javaworld.instagram.userinfoservice.service;

import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.service.dto.ProfileDetails;

import reactor.core.publisher.Mono;

public interface UserService {

	Mono<UserEntity> createUser(UserEntity userEntity);

	Mono<ProfileDetails> getProfileDetails(String userName);

}
