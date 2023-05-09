package com.javaworld.instagram.userinfoservice.integration;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaworld.instagram.userinfoservice.caching.InstaCache;
import com.javaworld.instagram.userinfoservice.commons.exceptions.HttpErrorInfo;
import com.javaworld.instagram.userinfoservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.userinfoservice.commons.exceptions.NotFoundException;
import com.javaworld.instagram.userinfoservice.configuration.PropertiesConfig;
import com.javaworld.instagram.userinfoservice.service.dto.PostsCountResponse;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import reactor.core.publisher.Mono;

@Service
public class PostServiceIntegrationImpl implements PostServiceIntegration {

	private static final Logger logger = LoggerFactory.getLogger(PostServiceIntegrationImpl.class);

	private final WebClient webClient;

	private final ObjectMapper mapper;

	@Autowired
	private PropertiesConfig propertiesConfig;

	@Autowired
	public PostServiceIntegrationImpl(WebClient.Builder webClientBuilder, ObjectMapper mapper) {
		this.webClient = webClientBuilder.build();
		this.mapper = mapper;
	}

	@Override
	@Retry(name = "postsCount")
	@TimeLimiter(name = "postsCount")
	@CircuitBreaker(name = "postsCount", fallbackMethod = "getPostsCountFallbackValue")
	public Mono<PostsCountResponse> getPostsCountByUserUuid(UUID userUuid, int delay, int faultPercent) {

		URI url = UriComponentsBuilder.fromUriString(propertiesConfig.getVirtualPostServiceUrl()
	    	      + "/posts/count?userUuid={userUuid}&delay={delay}&faultPercent={faultPercent}").build(userUuid, delay, faultPercent);

		logger.info("Will call the findPostsCount API on URL: {}", url);
		
		return webClient.get().uri(url).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(PostsCountResponse.class)
				.onErrorMap(WebClientResponseException.class, ex -> handleException(ex));
				
	}

	//TODO: suppress the warning
	private Mono<PostsCountResponse> getPostsCountFallbackValue(UUID userUuid, int delay, int faultPercent, CallNotPermittedException ex) {

		logger.warn(
				"Creating a fail-fast fallback postsCount for userUuid = {}, delay = {}, faultPercent = {} and exception = {} ",
				userUuid, delay, faultPercent, ex.toString());

		if(!InstaCache.postsCountCacheContainsKey(userUuid)) {
			String errMsg = "posts count for userUuid: " + userUuid.toString() + " not found in fallback cache!";
			logger.warn(errMsg);
			throw new NotFoundException(errMsg);
		}
		
	    int fallbackValue = InstaCache.getPostsCount(userUuid);
	    
	    PostsCountResponse postsCountResponse = new PostsCountResponse();
	    postsCountResponse.setPostsCount(fallbackValue);
	    
	    return Mono.just(postsCountResponse);
	}

	//TODO: move to a utility class
	private Throwable handleException(Throwable ex) {

		logger.info("starting handleException()..");

		if (!(ex instanceof WebClientResponseException)) {
			logger.warn("Got a unexpected error: {}, will rethrow it", ex.toString());
			return ex;
		}

		WebClientResponseException wcre = (WebClientResponseException) ex;

		switch (wcre.getStatusCode()) {

		case NOT_FOUND:
			return new NotFoundException(getErrorMessage(wcre));

		case UNPROCESSABLE_ENTITY:
			return new InvalidInputException(getErrorMessage(wcre));

		default:
			logger.warn("Got an unexpected HTTP error: {}, will rethrow it", wcre.getStatusCode());
			logger.warn("Error body: {}", wcre.getResponseBodyAsString());
			return ex;
		}
	}

	private String getErrorMessage(WebClientResponseException ex) {
		try {
			return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
		} catch (IOException ioex) {
			return ex.getMessage();
		}
	}

}
