package com.javaworld.instagram.postservice.features.post.service;

import java.util.List;

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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import static java.util.logging.Level.FINE;

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

	@Override
	public Flux<PostEntity> getPosts(int userId) {

		if (userId < 1) {
			throw new InvalidInputException("Invalid userId: " + userId);
		}

		logger.info("Will get posts for user with id={}", userId);

		return Mono.fromCallable(() -> internalGetPosts(userId))
				.flatMapMany(Flux::fromIterable)
				.log(logger.getName(), FINE)
				.subscribeOn(jdbcScheduler);

	}
	
	private List<PostEntity> internalGetPosts(int userId) {

		List<PostEntity> entityList = postRepository.findByUserId(userId);

		//TODO: the mapping to api dto & setting the service address should be done at controller
		
		// List<Review> list = mapper.entityListToApiList(entityList);
		// list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));

		logger.debug("Response size: {}", entityList.size());

		return entityList;
	}

}
