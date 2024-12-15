package com.javaworld.instagram.commonlib.messaging;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;

import org.springframework.messaging.support.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;

@Component
public class MessageSender {

	private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

	@Autowired
	private StreamBridge streamBridge;

	public void sendMessage(String bindingName, Event<UUID, Object> event) {
		logger.info("Sending a {} message to {}", event.getEventType(), bindingName);
		
		Message message = MessageBuilder.withPayload(event)
				                        .setHeader("partitionKey", event.getKey())
				                        .build();
		
		streamBridge.send(bindingName, message);
	}

}
