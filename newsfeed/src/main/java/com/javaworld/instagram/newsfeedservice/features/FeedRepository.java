package com.javaworld.instagram.newsfeedservice.features;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<FeedEntity, Integer> {

}
