package com.javaworld.instagram.newsfeedservice.features;
import java.util.UUID;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.javaworld.instagram.commonlib.exception.EventProcessingException;
import com.javaworld.instagram.commonlib.messaging.Event;

@Configuration
public class FeedFanoutWorker {

	private static final Logger logger = LoggerFactory.getLogger(FeedFanoutWorker.class);

    //@Autowired
    //private UserService userService; // Fetch followers

	@Autowired
	private FeedRepository feedRepository;

	
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
