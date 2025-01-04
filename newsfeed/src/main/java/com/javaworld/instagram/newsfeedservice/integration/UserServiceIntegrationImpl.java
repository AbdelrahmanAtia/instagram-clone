package com.javaworld.instagram.newsfeedservice.integration;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.javaworld.instagram.commonlib.config.PropertiesConfig;

import reactor.core.publisher.Mono;


@Service
public class UserServiceIntegrationImpl implements UserServiceIntegration {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceIntegrationImpl.class);

	private final WebClient webClient;

	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@Autowired
	public UserServiceIntegrationImpl(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}	

	@Override
	public Mono<List<UUID>> getUserFollowersIds(UUID userId) {

		URI url = UriComponentsBuilder
				.fromUriString(propertiesConfig.getVirtualUserServiceUrl() + "/int/users/{userUuid}/followersIds")
				.build(userId);

		logger.info("calling api on url on URL: {}", url);

		ParameterizedTypeReference<List<UUID>> responseType = 
				new ParameterizedTypeReference<List<UUID>>() {};

		//TODO: make it an internal api in the user service that doesn't need authorization
		return webClient.get().uri(url)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(responseType);

	}

}
