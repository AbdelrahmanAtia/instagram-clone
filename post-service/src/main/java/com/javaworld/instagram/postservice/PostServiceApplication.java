package com.javaworld.instagram.postservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableJpaAuditing
public class PostServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(PostServiceApplication.class, args);

		// print database URL after service starting
		String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");
		String mysqlUserName = ctx.getEnvironment().getProperty("spring.datasource.username");

		logger.info("Connected to MySQL: " + mysqlUri + " with Username: " + mysqlUserName);

	}

	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
	 
}
