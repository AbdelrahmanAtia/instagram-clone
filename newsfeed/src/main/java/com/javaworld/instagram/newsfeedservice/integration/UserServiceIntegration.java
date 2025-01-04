package com.javaworld.instagram.newsfeedservice.integration;

import java.util.List;
import java.util.UUID;

import reactor.core.publisher.Mono;

public interface UserServiceIntegration {

	Mono<List<UUID>> getUserFollowersIds(UUID userId);

}
