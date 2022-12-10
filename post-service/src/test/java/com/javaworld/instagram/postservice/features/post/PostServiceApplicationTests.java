package com.javaworld.instagram.postservice.features.post;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.javaworld.instagram.postservice.features.persistence.repositories.PostRepository;

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

	@Test
	void getPostsByUserId() {

		int userId = 1;

		assertEquals(0, repository.findByUserId(userId).size());

		postAndVerifyPost(userId, 1, OK);
		postAndVerifyPost(userId, 2, OK);
		postAndVerifyPost(userId, 3, OK);

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
	  
	  private WebTestClient.BodyContentSpec getAndVerifyPostsByUserId(int productId, HttpStatus expectedStatus) {
	    return getAndVerifyReviewsByProductId("?productId=" + productId, expectedStatus);
	  }


	  private WebTestClient.BodyContentSpec getAndVerifyReviewsByProductId(String productIdQuery, HttpStatus expectedStatus) {
	    return client.get()
	      .uri("/review" + productIdQuery)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
	  }

	  private WebTestClient.BodyContentSpec postAndVerifyPost(int userId, int postId, HttpStatus expectedStatus) {
	    Review review = new Review(productId, reviewId, "Author " + reviewId, "Subject " + reviewId, "Content " + reviewId, "SA");
	    return client.post()
	      .uri("/review")
	      .body(just(review), Review.class)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
	  }

		private WebTestClient.BodyContentSpec deleteAndVerifyReviewsByProductId(int userId, HttpStatus expectedStatus) {
			return client.delete()
					.uri("/post?userId=" + userId)
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isEqualTo(expectedStatus)
					.expectBody();
		}
	}