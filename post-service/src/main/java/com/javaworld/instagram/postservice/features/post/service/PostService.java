package com.javaworld.instagram.postservice.features.post.service;

import com.javaworld.instagram.postservice.features.post.persistence.PostEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostService {
	
	Mono<PostEntity> createPost(PostEntity postEntity);
	
	Flux<PostEntity> getPosts(int userId);
	
	Mono<Void> deletePosts(int userId);

}
