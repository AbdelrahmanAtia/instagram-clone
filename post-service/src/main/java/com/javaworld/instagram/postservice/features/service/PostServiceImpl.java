package com.javaworld.instagram.postservice.features.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private PostMapper postMapper;

	@Override
	@Transactional
	public Post createPost(Post post) {

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

			Post savedPost = postMapper.entityToDto(postEntity);

			logger.info("createPost: created a post entity");

			return savedPost;

		} catch (DataIntegrityViolationException dive) {
			// TODO: is this error message correct?
			throw new InvalidInputException("Duplicate key, Post UUID: " + post.getPostUuid());
		}
	}

	@Override
	@Transactional // TODO: make it read only
	public List<Post> getPosts(int userId) {

		logger.info("Will get posts for user with id={}", userId);

		List<PostEntity> entityList = postRepository.findByUserIdOrderByCreatedAtDesc(userId);

		return postMapper.entityListToDtoList(entityList);

	}

	@Override
	@Transactional
	public void deletePosts(List<String> postStrUuids) {

		logger.debug("deletePosts: tries to delete posts with uuids", postStrUuids);

		postRepository.deleteByPostUuidIn(postMapper.mapStrUuidToUuidObj(postStrUuids));

	}

}
