package com.javaworld.instagram.postservice.features.post.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.features.post.persistence.PostEntity;
import com.javaworld.instagram.postservice.features.post.persistence.PostRepository;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

@Service
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	@Qualifier("jdbcScheduler")
	private Scheduler jdbcScheduler;

	@Override
	@Transactional
	public Mono<PostEntity> createPost(PostEntity postEntity) {
		
		return Mono.fromCallable(() -> internalCreatePost(postEntity))
				   .subscribeOn(jdbcScheduler);
	}

	private PostEntity internalCreatePost(PostEntity postEntity) {
		try {

			PostEntity newEntity = postRepository.save(postEntity);
			logger.debug("createPost: created a post entity");
			return newEntity;

		} catch (DataIntegrityViolationException dive) {
			// TODO: is this error message correct
			throw new InvalidInputException("Duplicate key");
		}
	}

}
