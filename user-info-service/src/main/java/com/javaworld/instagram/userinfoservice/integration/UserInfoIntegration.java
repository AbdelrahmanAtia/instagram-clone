package com.javaworld.instagram.userinfoservice.integration;

import static java.util.logging.Level.FINE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Component
public class UserInfoIntegration {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserInfoIntegration.class);

	
	private final WebClient webClient;
	
	private final String postsServiceUrl;
	
	@Value("${app.posts-service.host}") 
	private String postsServiceHost;
	
	@Value("${app.posts-service.port}") 
	private int productServicePort;
	
	@Autowired
	public UserInfoIntegration(WebClient.Builder webClient) {
		postsServiceUrl = "http://" + postsServiceHost + ":" + productServicePort;
		this.webClient = webClient.build();
	}
	
	public Mono<Integer> getUserPostsCount(int userId) {

	    String url = postsServiceUrl + "/posts/count?userId=" + userId;

	    LOG.debug("Will call the getPostsCount API on URL: {}", url);

	    // Return an empty result if something goes wrong to make it possible for the composite service to return partial responses
	    return webClient.get()
	    		.uri(url)
	    		.retrieve()
	    		.bodyToMono(PostsCountResponseApiDto.class)
	    		.map(e -> e.getPostsCount())
	    		.log(LOG.getName(), FINE)
	    		.onErrorResume(error -> Mono.empty());
	    
	}

}
