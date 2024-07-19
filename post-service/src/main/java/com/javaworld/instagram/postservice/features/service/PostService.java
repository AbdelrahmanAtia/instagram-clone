package com.javaworld.instagram.postservice.features.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.javaworld.instagram.postservice.features.service.dto.Comment;
import com.javaworld.instagram.postservice.features.service.dto.Post;

public interface PostService {

	Post createPost(Post post);

	Page<Post> getPosts(UUID userUuid, int pageNumber, int pageSize);

	int deletePosts(List<String> postUuid);
	
	int countPosts(UUID userUuid);

	void deletePostsByUserUuid(UUID userUuid);
	
	List<Comment> getPostComments(UUID postId);

}
