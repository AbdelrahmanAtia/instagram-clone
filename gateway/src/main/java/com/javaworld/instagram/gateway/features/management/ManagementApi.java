package com.javaworld.instagram.gateway.features.management;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaworld.instagram.gateway.appconfig.PropertiesConfig;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/management")
public class ManagementApi {

	private static final Logger logger = LoggerFactory.getLogger(ManagementApi.class);

	private final WebClient webClient;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	public ManagementApi(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	@GetMapping("/health/{serviceName}")
	public Mono<String> getServiceHealth(@PathVariable String serviceName) {
		String uri = getServiceUrl(ServiceName.fromString(serviceName)) + "/actuator/health";
		logger.info("will call service {} on uri {}", serviceName, uri);

		return webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.map(JsonNode::toString);
	}

	@GetMapping("/circuitbreakerevents/{serviceName}/{circuitBreakerName}")
	public Mono<String> getCircuitBreakerStateTransitions(@PathVariable String serviceName,
			@PathVariable String circuitBreakerName) {
		
		String uri = getServiceUrl(ServiceName.fromString(serviceName)) 
				+ "/actuator/circuitbreakerevents/" 
				+ circuitBreakerName
				+ "/STATE_TRANSITION" ;
		
		logger.info("will call service {} on uri {}", serviceName, uri);

		return webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.map(JsonNode::toString);
	}

	@GetMapping("/retryevents/{serviceName}")
	public Mono<String> getRetryEvents(@PathVariable String serviceName) {
		
		String uri = getServiceUrl(ServiceName.fromString(serviceName)) 
				+ "/actuator/retryevents"; 
		
		logger.info("will call service {} on uri {}", serviceName, uri);

		return webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.map(JsonNode::toString);
	}
	
	
	
	private String getServiceUrl(ServiceName serviceName) {
		switch (serviceName) {
		case USER_MS:
			return propertiesConfig.getVirtualUserInfoServiceUrl();
		case POST_MS:
			return propertiesConfig.getVirtualPostServiceUrl();
		default:
			throw new IllegalArgumentException("Unknown microservice name: " + serviceName);
		}

	}

}