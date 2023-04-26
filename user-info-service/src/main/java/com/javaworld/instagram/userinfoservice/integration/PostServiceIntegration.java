package com.javaworld.instagram.userinfoservice.integration;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PostServiceIntegration {

	CompletableFuture<Integer> getPostsCountByUserUuid(UUID userUuid, int delay, int faultPercent);

}
