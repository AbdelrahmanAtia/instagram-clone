package com.javaworld.instagram.newsfeedservice.integration;

import java.util.List;
import java.util.UUID;

import com.javaworld.instagram.newsfeedservice.dto.Post;

public interface PostIntegrationService {

	List<Post> getPosts(List<UUID> postsIds);
}
