package com.javaworld.instagram.postservice.features.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.features.messaging.Event;
import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;
import com.javaworld.instagram.postservice.features.persistence.entities.TagEntity;
import com.javaworld.instagram.postservice.features.persistence.repositories.PostRepository;
import com.javaworld.instagram.postservice.features.persistence.repositories.TagRepository;
import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.features.service.dtomapper.PostMapper;
import com.javaworld.instagram.postservice.features.messaging.Event.Type;
import org.springframework.cloud.stream.function.StreamBridge;

@Service
public class PostServiceImpl implements PostService {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private PostMapper postMapper;
	
	@Autowired
	private StreamBridge streamBridge;

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
			
			//messageProducer.sendMessage("queueName", savedPost);
			
			//TODO: add a validation that this message is sent only when post is 
			//created and not sent when updated..
			Event createPostEvent = new Event(Type.CREATE, savedPost.getPostUuid(), savedPost);
			sendMessage("posts-out-0", createPostEvent);

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
	
	private void sendMessage(String bindingName, Event event) {
		logger.info("Sending a {} message to {}", event.getEventType(), bindingName);
		Message message = MessageBuilder
				.withPayload(event)
				//TODO: find a suitable key later for partitioning
				//.setHeader("partitionKey", event.getKey())
				.build();
		streamBridge.send(bindingName, message);
	}

}
