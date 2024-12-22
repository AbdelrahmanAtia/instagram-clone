package com.javaworld.instagram.newsfeedservice.service;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaworld.instagram.commonlib.exception.EventProcessingException;
import com.javaworld.instagram.commonlib.messaging.Event;
import com.javaworld.instagram.newsfeedservice.dto.FeedDto;
import com.javaworld.instagram.newsfeedservice.persistence.entity.FeedEntity;
import com.javaworld.instagram.newsfeedservice.persistence.entity.FeedId;
import com.javaworld.instagram.newsfeedservice.persistence.repository.FeedRepository;

@Service
public class FeedFanoutWorker {

	private static final Logger logger = LoggerFactory.getLogger(FeedFanoutWorker.class);

    //@Autowired
    //private UserService userService; // Fetch followers

	@Autowired
	private FeedRepository feedRepository;

	
	@RabbitListener(queues = "created_posts")
	public void handleCreatingPost(Event<UUID, Object> event) {
		logger.info("consumed a new event message with key: {}", event.getKey());

		try {
			String feedDtoJsonString = (String) event.getData();

			ObjectMapper objectMapper = new ObjectMapper();

			FeedDto feedDto = objectMapper.readValue(feedDtoJsonString, FeedDto.class);
			logger.info("feedDto: {}", feedDto);

			UUID userId = feedDto.getUserUuid();

			List<UUID> userFollowersIds = new ArrayList<>(); // TODO: call user service to get all of the user followers

			List<FeedEntity> feedEntities = new ArrayList<>();

			userFollowersIds.forEach(followerId -> {

				FeedId feedId = new FeedId(feedDto.getUserUuid(), feedDto.getPostUuid());

				FeedEntity feedEntity = new FeedEntity();
				feedEntity.setId(feedId);
				feedEntities.add(feedEntity);
			});

			feedRepository.saveAll(feedEntities);

		} catch (Exception ex) {
			logger.error("an error occured while consuming message from created_posts topic");
			ex.printStackTrace();
		}

	}
	
	@Bean
	public Consumer<Event<UUID, Object>> consumeCreatedPost() {
		
		return event -> { 

			logger.info("Process message created at {}...", event.getEventCreatedAt());
			
			
			switch (event.getEventType()) { 

			case CREATE:
				System.out.println("-------------- consumed message details -----------------");
				System.out.println("Key: " + event.getKey());
				System.out.println("Data: " + event.getData());
				System.out.println("-------------- End --------------------------------------");
				break;

			default:
				String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a CREATE event";
				logger.warn(errorMessage);
				throw new EventProcessingException(errorMessage);
			}

			//LOG.info("Message processing done!");
		};
	}
	
	/*
    public void handlePost(ConsumerRecord<String, String> record) {
        String userId = record.key();
        String postContent = record.value();

        List<String> followers = userService.getFollowers(userId);

        followers.forEach(followerId -> {
            feedRepository.saveToFeed(followerId, postContent);
        });
    }
    */
}
