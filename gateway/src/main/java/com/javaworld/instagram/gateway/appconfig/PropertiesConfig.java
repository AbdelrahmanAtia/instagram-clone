package com.javaworld.instagram.gateway.appconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

	@Value("${insta.post-service.virtual-url}")
	private String virtualPostServiceUrl; // Service URL when the Load Balancer calls EUREKA

	@Value("${insta.user-info-service.virtual-url}")
	private String virtualUserInfoServiceUrl; // Service URL when the Load Balancer calls EUREKA

	public String getVirtualPostServiceUrl() {
		return virtualPostServiceUrl;
	}

	public String getVirtualUserInfoServiceUrl() {
		return virtualUserInfoServiceUrl;
	}

}
