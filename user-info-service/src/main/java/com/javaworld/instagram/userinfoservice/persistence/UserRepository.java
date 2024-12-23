package com.javaworld.instagram.userinfoservice.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserEntity, String>, JpaSpecificationExecutor<UserEntity> {

	Optional<UserEntity> findByUserUuid(UUID userUuid);

	int deleteByUserUuid(UUID userUuid);

	@Query("SELECT u FROM UserEntity u WHERE u.userUuid NOT IN "
	        + "(SELECT f.followed.userUuid FROM FollowerEntity f WHERE f.follower.userUuid = :currentUserUuid) "
	        + "AND u.userUuid <> :currentUserUuid "
	        + "ORDER BY RAND()")
	List<UserEntity> findSuggestedUsers(UUID currentUserUuid, Pageable pageable);
	
	@Query("SELECT u FROM UserEntity u WHERE u.userUuid IN :userUuids")
	List<UserEntity> findUsersByUuids(@Param("userUuids") List<UUID> userUuids);

}
