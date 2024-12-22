package com.javaworld.instagram.newsfeedservice.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaworld.instagram.newsfeedservice.persistence.entity.FeedEntity;

public interface FeedRepository extends JpaRepository<FeedEntity, Integer> {

}
