package com.javaworld.instagram.newsfeedservice.integration;

import java.util.List;
import java.util.UUID;

public interface PostIntegrationService {

	void getPosts(List<UUID> postsIds);
}
