package com.javaworld.instagram.postservice.appconfig;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DIRECT_EXCHANGE_NAME = "direct_exchange_for_created_posts";

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    Queue createdPostsQueue() {
        return new Queue("created_posts", true);
    }

    @Bean
    Binding createdPostsBinding(Queue createdPostsQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(createdPostsQueue).to(directExchange).with("posts.created");
    }
    
}
