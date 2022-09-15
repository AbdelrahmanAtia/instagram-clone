package com.javaworld.instagram.postservice.features.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.features.post.persistence.PostEntity;
import com.javaworld.instagram.postservice.features.post.persistence.PostRepository;

import reactor.core.publisher.Mono;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	@Transactional
	public Mono<PostEntity> createPost(PostEntity postEntity) {
		
		//TODO: use JDBC scheduler that is described in the book
		return postRepository.save(postEntity);
	}

}
