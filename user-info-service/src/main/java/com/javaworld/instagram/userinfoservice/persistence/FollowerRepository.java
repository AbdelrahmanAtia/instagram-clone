package com.javaworld.instagram.userinfoservice.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FollowerRepository extends CrudRepository<FollowerEntity, String> {

	@Query("SELECT f FROM FollowerEntity f WHERE f.follower.id = :followerId AND f.followed.id = :followedId")
	Optional<FollowerEntity> findByFollowerIdAndFollowedId(@Param("followerId") UUID followerId,
			@Param("followedId") UUID followedId);

}
