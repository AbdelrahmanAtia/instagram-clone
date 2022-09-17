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
import com.javaworld.instagram.postservice.server.dto.PostApiDto;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PostServiceApplicationTests /*extends MySqlTestBase*/ {

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

    postAndVerifyThePost(userId, UUID.randomUUID(), OK);
    postAndVerifyThePost(userId, UUID.randomUUID(), OK);
    postAndVerifyThePost(userId, UUID.randomUUID(), OK);

    assertEquals(3, repository.findByUserId(userId).size());

    getAndVerifyPostsByUserId(userId, OK)
      .jsonPath("$.length()").isEqualTo(3)
      .jsonPath("$[2].userId").isEqualTo(userId)
      .jsonPath("$[2].id").isEqualTo(3);
  }
  
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
  private WebTestClient.BodyContentSpec postAndVerifyThePost(int userId, UUID postUuid, HttpStatus expectedStatus) {
	    PostApiDto post = new PostApiDto();
	    post.setTitle("post with uuid " + postUuid + " title");
	    post.setUserId(userId);
	    
	    return client.post()
	      .uri("/posts/")
	      .body(just(post), PostApiDto.class)
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();	 
  }
  
  
  private WebTestClient.BodyContentSpec getAndVerifyPostsByUserId(int userId, HttpStatus expectedStatus) {
	    return client.get()
	      .uri("/posts" + userId)  //TODO : implement the service /posts/{userId} so that this test can pass
	      .accept(APPLICATION_JSON)
	      .exchange()
	      .expectStatus().isEqualTo(expectedStatus)
	      .expectHeader().contentType(APPLICATION_JSON)
	      .expectBody();
  }
  
}