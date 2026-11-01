package com.javaworld.instagram.userinfoservice.integration;

import java.util.UUID;

import com.javaworld.instagram.userinfoservice.service.dto.PostsCountResponse;

import reactor.core.publisher.Mono;

public interface PostServiceIntegration {

	void deletePostsOfCurrentUser(UUID userUuid);

}
