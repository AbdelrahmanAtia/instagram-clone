package com.javaworld.instagram.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ConfigServerApplication {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigServerApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ConfigServerApplication.class, args);

		String repoLocation = ctx.getEnvironment().getProperty("spring.cloud.config.server.native.searchLocations");
		LOG.info("Serving configurations from folder: " + repoLocation);
	}

}
