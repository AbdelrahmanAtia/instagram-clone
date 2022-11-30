package com.javaworld.instagram.postservice.features.service;

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
	public void createPost(Post post) {

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
	@Transactional // TODO: make it read only
	public List<Post> getPosts(int userId) {

		if (userId < 1) {
			throw new InvalidInputException("Invalid userId: " + userId);
		}

		logger.info("Will get posts for user with id={}", userId);

		List<PostEntity> entityList = postRepository.findByUserId(userId);

		return postMapper.entityListToDtoList(entityList);

	}

	@Override
	public void deletePosts(int userId) {

		if (userId < 1) {
			throw new InvalidInputException("Invalid userId: " + userId);
		}

		logger.debug("deletePosts: tries to delete posts for the user with userId: {}", userId);

		postRepository.deleteAll(postRepository.findByUserId(userId));
		
	}

}
