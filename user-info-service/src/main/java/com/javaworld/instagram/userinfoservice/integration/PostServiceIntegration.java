package com.javaworld.instagram.userinfoservice.integration;

import java.util.UUID;

import com.javaworld.instagram.userinfoservice.service.dto.PostsCountResponse;

import reactor.core.publisher.Mono;

public interface PostServiceIntegration {

	Mono<PostsCountResponse> getPostsCountByUserUuid(UUID userUuid, int delay, int faultPercent);

}
