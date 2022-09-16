package com.javaworld.instagram.postservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
//@EnableEurekaClient
//@EnableCircuitBreaker
public class PostServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceApplication.class);

	@Value("${app.threadPoolSize:10}")
	private Integer threadPoolSize;

	@Value("${app.taskQueueSize:100}")
	private Integer taskQueueSize;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(PostServiceApplication.class, args);

		// print database URL after service starting
		String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");
		String mysqlUserName = ctx.getEnvironment().getProperty("spring.datasource.username");

		logger.info("Connected to MySQL: " + mysqlUri + " with Username: " + mysqlUserName);

	}

	@Bean
	public Scheduler jdbcScheduler() {
		logger.info("Creates a jdbcScheduler with thread pool size = {}", threadPoolSize);
		return Schedulers.newBoundedElastic(threadPoolSize, taskQueueSize, "jdbc-pool");
	}

}
