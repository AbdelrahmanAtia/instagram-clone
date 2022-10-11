package com.javaworld.instagram.postservice.features.service;

import static java.util.logging.Level.FINE;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;
import com.javaworld.instagram.postservice.features.persistence.entities.TagEntity;
import com.javaworld.instagram.postservice.features.persistence.repositories.PostRepository;
import com.javaworld.instagram.postservice.features.persistence.repositories.TagRepository;
import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.features.service.dtomapper.PostMapper;

import reactor.core.publisher.Flux;
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
	
	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private PostMapper postMapper;
	

	@Override
	@Transactional
	public Mono<Void> createPost(Post post) {
		return Mono.fromRunnable(() -> internalCreatePost(post))
				.subscribeOn(jdbcScheduler).then();
	}

	private void internalCreatePost(Post post) {
		try {

			List<TagEntity> tagEntityList = postMapper.dtoListToEntityList(post.getTags());
			List<TagEntity> savedTags = new ArrayList<>();

			tagEntityList.forEach(t -> {
				tagRepository.findByName(t.getName()).ifPresentOrElse(e -> savedTags.add(e),
						() -> savedTags.add(tagRepository.save(t)));
			});

			PostEntity postEntity = postMapper.dtoToEntity(post);
			savedTags.forEach(t -> postEntity.addPostTagAssignment(t));
			postRepository.save(postEntity);

			logger.debug("createPost: created a post entity");

		} catch (DataIntegrityViolationException dive) {
			// TODO: is this error message correct
			throw new InvalidInputException("Duplicate key");
		}
	}

	@Override
	@Transactional  //TODO: make it read only
	public Flux<Post> getPosts(int userId) {

		if (userId < 1) {
			throw new InvalidInputException("Invalid userId: " + userId);
		}

		logger.info("Will get posts for user with id={}", userId);

		return Mono.fromCallable(() -> internalGetPosts(userId))
				.map(e -> postMapper.entityListToDtoList(e))
				.flatMapMany(Flux::fromIterable)
				.log(logger.getName(), FINE)
				.subscribeOn(jdbcScheduler);

	}
	
	private List<PostEntity> internalGetPosts(int userId) {
		List<PostEntity> entityList = postRepository.findByUserId(userId);
		logger.debug("Response size: {}", entityList.size());
		return entityList;
	}

	@Override
	public Mono<Void> deletePosts(int userId) {

		if (userId < 1) {
			throw new InvalidInputException("Invalid userId: " + userId);
		}

		return Mono.fromRunnable(() -> internalDeletePosts(userId)).subscribeOn(jdbcScheduler).then();
	}

	private void internalDeletePosts(int userId) {

		logger.debug("deletePosts: tries to delete posts for the user with userId: {}", userId);

		postRepository.deleteAll(postRepository.findByUserId(userId));
	}

}
