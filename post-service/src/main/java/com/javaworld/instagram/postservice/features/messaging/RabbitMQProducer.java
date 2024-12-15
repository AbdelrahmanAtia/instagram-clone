package com.javaworld.instagram.postservice.features.messaging;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

	private final AmqpTemplate amqpTemplate;

	@Autowired
	public RabbitMQProducer(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	public void sendMessage(String routingKey, String message) {
		amqpTemplate.convertAndSend("direct_exchange_for_created_posts", routingKey, message);
		System.out.println("Sent message: " + message + " with routing key: " + routingKey);
	}
}
