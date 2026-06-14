package com.javaworld.instagram.configserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * if the environment check fails, then the config server will fail to start.
 * it is mandatory to pass the env variables or the .env file when starting
 * the config server
 */
@Component
public class EnvCheck implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(EnvCheck.class);

    private final Environment environment;

    public EnvCheck(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(String... args) {
        String configServerUser = environment.getProperty("CONFIG_SERVER_USR");
        String configServerPwd = environment.getProperty("CONFIG_SERVER_PWD");

        if (configServerUser == null || configServerPwd == null) {
            throw new RuntimeException(".env NOT loaded or env variable missing.");
        } else {
            logger.info(".env loaded");
        }
    }
}