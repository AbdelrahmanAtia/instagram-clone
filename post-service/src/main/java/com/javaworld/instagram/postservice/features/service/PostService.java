package com.javaworld.instagram.postservice.features.service;

import com.javaworld.instagram.postservice.features.service.dto.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {

	Mono<Void> createPost(Post post);

	Flux<Post> getPosts(int userId);

	Mono<Void> deletePosts(int userId);

}
