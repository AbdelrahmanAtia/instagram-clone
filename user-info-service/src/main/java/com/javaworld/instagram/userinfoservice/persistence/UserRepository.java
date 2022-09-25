package com.javaworld.instagram.userinfoservice.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, String> {

}
