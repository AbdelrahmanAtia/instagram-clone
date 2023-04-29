package com.javaworld.instagram.userinfoservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

	@Value("${insta.post-service.url}")
	private String postServiceUrl;

	@Value("${insta.post-service.virtual-url}")
	private String virtualPostServiceUrl; // Service URL when the Load Balancer calls EUREKA

	public String getPostServiceUrl() {
		return postServiceUrl;
	}

	public String getVirtualPostServiceUrl() {
		return virtualPostServiceUrl;
	}

}
