package com.javaworld.instagram.userinfoservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class UserInfoServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(UserInfoServiceApplication.class, args);

		// print database URL after service starting
		String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");
		String mysqlUserName = ctx.getEnvironment().getProperty("spring.datasource.username");

		logger.info("Connected to MySQL: " + mysqlUri + " with Username: " + mysqlUserName);
	}	
	
	@Bean
	public OAuth2AuthorizedClientManager authorizedClientManager(
			ClientRegistrationRepository clientRegistrationRepository,
			OAuth2AuthorizedClientRepository authorizedClientRepository) {

		OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
				.clientCredentials().build();

		DefaultOAuth2AuthorizedClientManager authorizedClientManager = new DefaultOAuth2AuthorizedClientManager(
				clientRegistrationRepository, authorizedClientRepository);
		
		authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

		return authorizedClientManager;
	}
	
	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder(OAuth2AuthorizedClientManager authorizedClientManager) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction filter = new ServletOAuth2AuthorizedClientExchangeFilterFunction(
				authorizedClientManager);
		
		filter.setDefaultClientRegistrationId("mywebclient");

		return WebClient.builder().apply(filter.oauth2Configuration());

	}	

	
	/*
    // This method returns filter function which will log request data
	private static ExchangeFilterFunction logRequest() {
	
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			System.out.println("--------------------------------------");
			logger.info("##########################################");
			logger.info("Request: {} {}", clientRequest.method(), clientRequest.url());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
			return Mono.just(clientRequest);
		});
	}
	*/

}
