package com.javaworld.instagram.commonlib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class InstaCloneCommonLibApp {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(InstaCloneCommonLibApp.class, args);
		
	}	
	
}
