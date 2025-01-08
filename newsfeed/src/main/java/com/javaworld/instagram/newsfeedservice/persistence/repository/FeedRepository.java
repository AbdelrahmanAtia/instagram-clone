package com.javaworld.instagram.newsfeedservice.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaworld.instagram.newsfeedservice.persistence.entity.FeedEntity;
import com.javaworld.instagram.newsfeedservice.persistence.entity.FeedId;

public interface FeedRepository extends JpaRepository<FeedEntity, FeedId> {

	//@Query("SELECT f.id.postUuid FROM FeedEntity f WHERE f.id.userUuid = :userId")
	//List<UUID> findPostIdsByUserId(UUID userId);

	List<UUID> findByIdUserUuid(UUID userId);
}
