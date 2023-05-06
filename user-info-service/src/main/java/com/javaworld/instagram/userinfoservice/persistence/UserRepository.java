package com.javaworld.instagram.userinfoservice.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> {

	Optional<UserEntity> findByUserUuid(UUID userUuid);
	
	int deleteByUserUuid(UUID userUuid);

}
