package com.javaworld.instagram.postservice.features.service;

import java.util.List;

import com.javaworld.instagram.postservice.features.service.dto.Post;

public interface PostService {

	Post createPost(Post post);

	List<Post> getPosts(int userId);

	void deletePosts(List<String> postUuid);

}
