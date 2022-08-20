package com.javaworld.instagram.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
//@EnableEurekaClient
//@EnableCircuitBreaker
public class PostServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(PostServiceApplication.class, args);

		String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");
		String mysqlUserName = ctx.getEnvironment().getProperty("spring.datasource.username");

		logger.info("Connected to MySQL: " + mysqlUri + " with Username: " + mysqlUserName);

	}

}
