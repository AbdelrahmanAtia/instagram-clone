package com.javaworld.instagram.postservice.features.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.javaworld.instagram.postservice.features.post.persistence.PostRepository;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostServiceApplicationTests /* extends MySqlTestBase */ {

	@Autowired
	private WebTestClient client;

	@Autowired
	private PostRepository repository;

	@BeforeEach
	void setupDb() {
		repository.deleteAll();
	}

	// ###################################################################################//
	// TODO: this test needs to be completed after you finish the messaging part of
	// of chapter 7 of magnus larson book..
	// ###################################################################################//

}