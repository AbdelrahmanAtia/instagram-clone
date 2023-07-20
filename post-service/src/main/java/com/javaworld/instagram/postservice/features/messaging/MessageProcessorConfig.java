package com.javaworld.instagram.postservice.features.messaging;

import java.util.UUID;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javaworld.instagram.postservice.commons.exceptions.EventProcessingException;
import com.javaworld.instagram.postservice.features.service.PostService;

@Configuration
public class MessageProcessorConfig {

	private static final Logger LOG = LoggerFactory.getLogger(MessageProcessorConfig.class);

	@Autowired
	private PostService postService;   

	@Bean
	public Consumer<Event<UUID, Object>> messageProcessor() {
		 
		
		return event -> {

			LOG.info("Process message created at {}...", event.getEventCreatedAt());

			switch (event.getEventType()) { 

			case DELETE:
				UUID userUuid = event.getKey();
				LOG.info("Delete posts for user with uuid: {}", userUuid);
				postService.deletePostsByUserUuid(userUuid);
				break;

			default:
				String errorMessage = "Incorrect event type: " + event.getEventType() + ", expected a DELETE event";
				LOG.warn(errorMessage);
				throw new EventProcessingException(errorMessage);
			}

			LOG.info("Message processing done!");
		};
	}
}