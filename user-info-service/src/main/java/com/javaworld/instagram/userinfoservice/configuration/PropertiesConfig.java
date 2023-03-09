package com.javaworld.instagram.userinfoservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

	@Value("${insta.post-service.url}")
	private String postServiceUrl;

	@Value("${insta.post-service.virtual-url}")
	private String virtualPostServiceUrl; // Service URL when the Load Balancer calls EUREKA

	@Value("${server.servlet.context-path}")
	private String servicesContext;

	public String getPostServiceUrl() {
		return postServiceUrl;
	}

	public void setPostServiceUrl(String postServiceUrl) {
		this.postServiceUrl = postServiceUrl;
	}

	public String getServicesContext() {
		return servicesContext;
	}

	public void setServicesContext(String servicesContext) {
		this.servicesContext = servicesContext;
	}

	public String getVirtualPostServiceUrl() {
		return virtualPostServiceUrl;
	}

	public void setVirtualPostServiceUrl(String virtualPostServiceUrl) {
		this.virtualPostServiceUrl = virtualPostServiceUrl;
	}

}
