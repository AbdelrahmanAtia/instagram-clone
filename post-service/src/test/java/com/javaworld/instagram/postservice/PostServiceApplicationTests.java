package com.javaworld.instagram.postservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.javaworld.instagram.postservice.features.persistence.repositories.PostRepository;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;
import com.javaworld.instagram.postservice.server.dto.TagApiDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostServiceApplicationTests /* extends MySqlTestBase */ {
	
	private static final String CONTEXT_PATH = "";
	
	@Autowired
	private WebTestClient client;

	@Autowired
	private PostRepository repository;

	@BeforeEach
	void setupDb() {
		repository.deleteAll();
	}

	@Test
	void getPostsByUserId() {

		int userId = 1;
		
		UUID firstPostUUID = UUID. randomUUID();
		UUID secondPostUUID = UUID. randomUUID();
		UUID thirdPostUUID = UUID. randomUUID();		

		assertEquals(0, repository.findByUserId(userId).size());

		postAndVerifyPost(userId, firstPostUUID, OK);
		postAndVerifyPost(userId, secondPostUUID, OK);
		postAndVerifyPost(userId, thirdPostUUID, OK);

		assertEquals(3, repository.findByUserId(userId).size());

		getAndVerifyPostsByUserId(userId, OK)
			.jsonPath("$.length()").isEqualTo(3)
			.jsonPath("$[2].userId").isEqualTo(userId)
			.jsonPath("$[2].postId").isEqualTo(3);
	
	}

	  /*
	  @Test
	  void duplicateError() {

	    int productId = 1;
	    int reviewId = 1;

	    assertEquals(0, repository.count());

	    postAndVerifyReview(productId, reviewId, OK)
	      .jsonPath("$.productId").isEqualTo(productId)
	      .jsonPath("$.reviewId").isEqualTo(reviewId);

	    assertEquals(1, repository.count());

	    postAndVerifyReview(productId, reviewId, UNPROCESSABLE_ENTITY)
	      .jsonPath("$.path").isEqualTo("/review")
	      .jsonPath("$.message").isEqualTo("Duplicate key, Product Id: 1, Review Id:1");

	    assertEquals(1, repository.count());
	  }

	  @Test
	  void deleteReviews() {

	    int productId = 1;
	    int reviewId = 1;

	    postAndVerifyReview(productId, reviewId, OK);
	    assertEquals(1, repository.findByProductId(productId).size());

	    deleteAndVerifyReviewsByProductId(productId, OK);
	    assertEquals(0, repository.findByProductId(productId).size());

	    deleteAndVerifyReviewsByProductId(productId, OK);
	  }

	  @Test
	  void getReviewsMissingParameter() {

	    getAndVerifyReviewsByProductId("", BAD_REQUEST)
	      .jsonPath("$.path").isEqualTo("/review")
	      .jsonPath("$.message").isEqualTo("Required int parameter 'productId' is not present");
	  }

	  @Test
	  void getReviewsInvalidParameter() {

	    getAndVerifyReviewsByProductId("?productId=no-integer", BAD_REQUEST)
	      .jsonPath("$.path").isEqualTo("/review")
	      .jsonPath("$.message").isEqualTo("Type mismatch.");
	  }

	  @Test
	  void getReviewsNotFound() {

	    getAndVerifyReviewsByProductId("?productId=213", OK)
	      .jsonPath("$.length()").isEqualTo(0);
	  }

	  @Test
	  void getReviewsInvalidParameterNegativeValue() {

	    int productIdInvalid = -1;

	    getAndVerifyReviewsByProductId("?productId=" + productIdInvalid, UNPROCESSABLE_ENTITY)
	      .jsonPath("$.path").isEqualTo("/review")
	      .jsonPath("$.message").isEqualTo("Invalid productId: " + productIdInvalid);
	  }
	  */
	  
	  private WebTestClient.BodyContentSpec getAndVerifyPostsByUserId(int userId, HttpStatus expectedStatus) {
	    return getAndVerifyPostsByUserId("? userId=" +  userId, expectedStatus);
	  }


	  private WebTestClient.BodyContentSpec getAndVerifyPostsByUserId(String userIdQuery, HttpStatus expectedStatus) {
	    return client.get()
	      .uri( CONTEXT_PATH + "/posts/" + userIdQuery)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
	  }

		private WebTestClient.BodyContentSpec postAndVerifyPost(int userId, UUID postUUID, HttpStatus expectedStatus) {
			
			TagApiDto tag1 = new TagApiDto();
			tag1.setName("java");
			
			TagApiDto tag2 = new TagApiDto();
			tag2.setName("OOP");
			
			List<TagApiDto> tagsApiDtoList = new ArrayList<>();
			tagsApiDtoList.add(tag1);
			tagsApiDtoList.add(tag2);
			
			PostApiDto postApiDto = new PostApiDto();
			postApiDto.setUserId(userId);
			postApiDto.setPostUUID(postUUID);
			postApiDto.setTags(tagsApiDtoList);

			return client.post()
					.uri(CONTEXT_PATH + "/posts/")
					.body(just(postApiDto), PostApiDto.class)
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isEqualTo(expectedStatus)
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBody();
		}

		private WebTestClient.BodyContentSpec deleteAndVerifyReviewsByProductId(int userId, HttpStatus expectedStatus) {
			return client.delete()
					.uri(CONTEXT_PATH + "/posts?userId=" + userId)
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isEqualTo(expectedStatus)
					.expectBody();
		}
	}