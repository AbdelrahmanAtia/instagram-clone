package com.javaworld.instagram.userinfoservice.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FollowerRepository extends CrudRepository<FollowerEntity, String> {  

	@Query("SELECT f FROM FollowerEntity f WHERE f.follower.userUuid = :followedId AND f.followed.userUuid = :followerId")
	Optional<FollowerEntity> findByFollowerIdAndFollowedId(@Param("followerId") UUID followerId,
			@Param("followedId") UUID followedId);
	
		
    @Query("SELECT COUNT(f) FROM FollowerEntity f WHERE f.follower.userUuid = :followedId")
	int getFollowersCount(@Param("followedId") UUID followedId);
    
    @Query("SELECT COUNT(f) FROM FollowerEntity f WHERE f.followed.userUuid = :followerId")
	int getFollowingCount(@Param("followerId") UUID followerId);
    
    @Query("SELECT f.followed.userUuid FROM FollowerEntity f WHERE f.follower.userUuid = :followedId")
    List<UUID> findFollowerIdsByFollowedId(@Param("followedId") UUID followedId);
    
	@Modifying
    @Transactional
    @Query("DELETE FROM FollowerEntity f WHERE f.follower.userUuid = :followedId AND f.followed.userUuid = :followerId")
	void deleteFollower(@Param("followedId") UUID followedId, @Param("followerId") UUID followerId);

}
