package com.javaworld.instagram.userinfoservice.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserEntity, String> {

	Optional<UserEntity> findByUserUuid(UUID userUuid);

	int deleteByUserUuid(UUID userUuid);

	@Query("SELECT u.postsCount FROM UserEntity u WHERE u.userUuid = :uuid")
	int getPostsCountByUserUuid(@Param("uuid") UUID userUuid);

}
