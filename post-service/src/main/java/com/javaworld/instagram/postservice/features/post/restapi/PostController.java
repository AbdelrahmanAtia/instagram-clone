package com.javaworld.instagram.postservice.features.post.restapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api")
public class PostController {

	
	
	/*
	@GetMapping("/users/{username}/posts")
	@HystrixCommand(fallbackMethod = "getFallbackUserPosts")
	public List<Post> getUserPosts(@PathVariable String username) throws InterruptedException {

		Thread.sleep(90000);
		
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("this is my first post");

		Post secondPost = new Post();
		secondPost.setId(2L);
		secondPost.setTitle("this is my second post");

		return Arrays.asList(firstPost, secondPost);
	}

	@GetMapping("/users/{username}/posts/{postId}")
	public Post getPost(@PathVariable String username, @PathVariable Long postId) {
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("this is my first post");
		return firstPost;
	}

	@GetMapping("/users/me/posts")
	public List<Post> getCurrentUserPosts() {

		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("this is my first post");

		Post secondPost = new Post();
		secondPost.setId(2L);
		secondPost.setTitle("this is my second post");

		return Arrays.asList(firstPost, secondPost);
	}

	public List<Post> getFallbackUserPosts(@PathVariable String username) {
		Post post = new Post();
		post.setId(0L);
		post.setTitle("this is a fallback post");
		
		return Arrays.asList(post);
	}
	
	*/

}
