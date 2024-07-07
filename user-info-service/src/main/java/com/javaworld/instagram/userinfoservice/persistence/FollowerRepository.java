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

	//followerId
	//followedId
	@Query("SELECT f FROM FollowerEntity f WHERE f.follower.userUuid = :followerId AND f.followed.userUuid = :followedId")
	Optional<FollowerEntity> findByFollowerIdAndFollowedId(@Param("followerId") UUID followerId,
			@Param("followedId") UUID followedId);
	
		
    @Query("SELECT COUNT(f) FROM FollowerEntity f WHERE f.followed.userUuid = :followedId")
	int getFollowersCount(@Param("followedId") UUID followedId);
    
    @Query("SELECT COUNT(f) FROM FollowerEntity f WHERE f.follower.userUuid = :followerId")
	int getFollowingCount(@Param("followerId") UUID followerId);
    
    @Query("SELECT f.follower.userUuid FROM FollowerEntity f WHERE f.followed.userUuid = :followedId")
    List<UUID> findFollowerIdsByFollowedId(@Param("followedId") UUID followedId);
    
    @Query("SELECT f.followed.userUuid FROM FollowerEntity f WHERE f.follower.userUuid = :followerId")
    List<UUID> findFollowingsIdsByFollowerId(@Param("followerId") UUID followerId);
    
	@Modifying
    @Transactional
    @Query("DELETE FROM FollowerEntity f WHERE f.follower.userUuid = :followerId AND f.followed.userUuid = :followedId")
	void delete(@Param("followerId") UUID followerId, @Param("followedId") UUID followedId);
	

}
