package com.javaworld.instagram.postservice.features.service;

import java.util.List;
import java.util.UUID;

import com.javaworld.instagram.postservice.features.service.dto.Post;

public interface PostService {

	Post createPost(Post post);

	List<Post> getPosts(UUID userUuid);

	void deletePosts(List<String> postUuid);

}
