/*
 * package com.javaworld.instagram.postservice;
 * 
 * import static org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.springframework.boot.test.context.SpringBootTest.WebEnvironment.
 * RANDOM_PORT; import static org.springframework.http.HttpStatus.OK; import
 * static org.springframework.http.MediaType.APPLICATION_JSON; import static
 * reactor.core.publisher.Mono.just;
 * 
 * import java.util.ArrayList; import java.util.List; import java.util.UUID;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.test.web.reactive.server.WebTestClient; import
 * org.springframework.test.web.reactive.server.WebTestClient.BodyContentSpec;
 * 
 * import com.fasterxml.jackson.core.JsonProcessingException; import
 * com.fasterxml.jackson.databind.ObjectMapper; import
 * com.javaworld.instagram.postservice.features.persistence.repositories.
 * PostRepository; import
 * com.javaworld.instagram.postservice.features.restapi.PostsApiImpl; import
 * com.javaworld.instagram.postservice.server.dto.PostApiDto; import
 * com.javaworld.instagram.postservice.server.dto.TagApiDto; import static
 * org.springframework.http.HttpStatus.*;
 * 
 * 
 * @SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
 * "eureka.client.enabled=false" //we don't want to depend on having EUREKA
 * server up and running in our tests //TODO: try to enable it and see what
 * happens }) class PostServiceApplicationTests extends MySqlTestBase {
 * 
 * private static final Logger logger =
 * LoggerFactory.getLogger(PostsApiImpl.class);
 * 
 * private static final String CONTEXT_PATH = "";
 * 
 * @Autowired private WebTestClient client;
 * 
 * @Autowired private PostRepository repository;
 * 
 * 
 * @BeforeEach void setupDb() { repository.deleteAll(); }
 * 
 * @Test void getPostsByUserId() {
 * 
 * int userId = 1;
 * 
 * String firstPostTitle = "first post title"; String secondPostTitle
 * ="second post title"; String thirdPostTitle = "third post title";
 * 
 * assertEquals(0, repository.findByUserId(userId).size());
 * 
 * postAndVerifyPost(userId, firstPostTitle, OK); postAndVerifyPost(userId,
 * secondPostTitle, OK); postAndVerifyPost(userId, thirdPostTitle, OK);
 * 
 * assertEquals(3, repository.findByUserId(userId).size());
 * 
 * getAndVerifyPostsByUserId(userId, OK) .jsonPath("$.length()").isEqualTo(3)
 * 
 * .jsonPath("$[2].userId").isEqualTo(userId)
 * .jsonPath("$[2].title").isEqualTo(firstPostTitle)
 * 
 * .jsonPath("$[1].userId").isEqualTo(userId)
 * .jsonPath("$[1].title").isEqualTo(secondPostTitle)
 * 
 * .jsonPath("$[0].userId").isEqualTo(userId)
 * .jsonPath("$[0].title").isEqualTo(thirdPostTitle);
 * 
 * }
 * 
 * 
 * @Test void duplicateErrorWhenCreatingPostsWithSameUUID() {
 * 
 * int userId = 1; UUID postUuid = UUID.randomUUID();
 * 
 * assertEquals(0, repository.count());
 * 
 * postAndVerifyPost(userId, postUuid, OK)
 * .jsonPath("$.postUuid").isEqualTo(postUuid.toString())
 * .jsonPath("$.userId").isEqualTo(userId);
 * 
 * assertEquals(1, repository.count());
 * 
 * postAndVerifyPost(userId, postUuid, UNPROCESSABLE_ENTITY)
 * .jsonPath("$.path").isEqualTo("/posts")
 * .jsonPath("$.message").isEqualTo("Duplicate key, Post UUID: " + postUuid);
 * 
 * assertEquals(1, repository.count());
 * 
 * }
 * 
 * @Test void deletePostsByPostUUID() {
 * 
 * int userId = 1; UUID firstPostUuid = UUID.randomUUID(); UUID secondPostUuid =
 * UUID.randomUUID();
 * 
 * postAndVerifyPost(userId, firstPostUuid, OK); postAndVerifyPost(userId,
 * secondPostUuid, OK);
 * 
 * assertEquals(2, repository.findByUserId(userId).size());
 * 
 * List<String> postsUuidList = new ArrayList<>();
 * postsUuidList.add(firstPostUuid.toString());
 * postsUuidList.add(secondPostUuid.toString());
 * 
 * deleteAndVerifyPostsByUuid(postsUuidList, OK); assertEquals(0,
 * repository.findByUserId(userId).size());
 * 
 * //issue delete again to validate that the delete service is idempotent
 * deleteAndVerifyPostsByUuid(postsUuidList, OK);
 * 
 * }
 * 
 * @Test void getPosts_MissingParameter() {
 * 
 * getAndVerifyPostsByUserId("", BAD_REQUEST)
 * .jsonPath("$.path").isEqualTo("/posts/findByUserId");
 * 
 * }
 * 
 * @Test void getPosts_InvalidParameter() {
 * 
 * getAndVerifyPostsByUserId("?userId=no-integer", BAD_REQUEST)
 * .jsonPath("$.path").isEqualTo("/posts/findByUserId");
 * 
 * }
 * 
 * @Test void getPostsByUserId_NotFound() {
 * 
 * getAndVerifyPostsByUserId("?userId=213", OK)
 * .jsonPath("$.length()").isEqualTo(0); }
 * 
 * 
 * @Test void getPostsByUserId_InvalidParameterNegativeValue() {
 * 
 * int userId = -1;
 * 
 * getAndVerifyPostsByUserId("?userId=" + userId, UNPROCESSABLE_ENTITY)
 * .jsonPath("$.path").isEqualTo("/posts/findByUserId")
 * .jsonPath("$.message").isEqualTo("Invalid userId: " + userId);
 * 
 * }
 * 
 * private WebTestClient.BodyContentSpec getAndVerifyPostsByUserId(int userId,
 * HttpStatus expectedStatus) { return getAndVerifyPostsByUserId("?userId=" +
 * userId, expectedStatus); }
 * 
 * 
 * private WebTestClient.BodyContentSpec getAndVerifyPostsByUserId(String
 * userIdQuery, HttpStatus expectedStatus) {
 * 
 * BodyContentSpec bodyContentSpec = client.get() .uri( CONTEXT_PATH +
 * "/posts/findByUserId" + userIdQuery) .accept(APPLICATION_JSON) .exchange()
 * .expectStatus().isEqualTo(expectedStatus)
 * .expectHeader().contentType(APPLICATION_JSON) .expectBody();
 * 
 * logWebTestClientResponse(bodyContentSpec);
 * 
 * return bodyContentSpec; }
 * 
 * private WebTestClient.BodyContentSpec postAndVerifyPost(int userId, String
 * title, HttpStatus expectedStatus) {
 * 
 * TagApiDto tag1 = new TagApiDto(); tag1.setName("java");
 * 
 * TagApiDto tag2 = new TagApiDto(); tag2.setName("OOP");
 * 
 * List<TagApiDto> tagsApiDtoList = new ArrayList<>(); tagsApiDtoList.add(tag1);
 * tagsApiDtoList.add(tag2);
 * 
 * PostApiDto postApiDto = new PostApiDto(); postApiDto.setUserId(userId);
 * postApiDto.setTitle(title); postApiDto.setTags(tagsApiDtoList);
 * 
 * return client.post() .uri(CONTEXT_PATH + "/posts") .body(just(postApiDto),
 * PostApiDto.class) .accept(APPLICATION_JSON) .exchange()
 * .expectStatus().isEqualTo(expectedStatus)
 * .expectHeader().contentType(APPLICATION_JSON) .expectBody(); }
 * 
 * private WebTestClient.BodyContentSpec postAndVerifyPost(int userId, UUID
 * postUuid, HttpStatus expectedStatus) {
 * 
 * TagApiDto tag1 = new TagApiDto(); tag1.setName("java");
 * 
 * TagApiDto tag2 = new TagApiDto(); tag2.setName("OOP");
 * 
 * List<TagApiDto> tagsApiDtoList = new ArrayList<>(); tagsApiDtoList.add(tag1);
 * tagsApiDtoList.add(tag2);
 * 
 * PostApiDto postApiDto = new PostApiDto(); postApiDto.setUserId(userId);
 * postApiDto.setPostUuid(postUuid); postApiDto.setTags(tagsApiDtoList);
 * 
 * BodyContentSpec bodyContentSpec = client.post() .uri(CONTEXT_PATH + "/posts")
 * .body(just(postApiDto), PostApiDto.class) .accept(APPLICATION_JSON)
 * .exchange() .expectStatus().isEqualTo(expectedStatus)
 * .expectHeader().contentType(APPLICATION_JSON) .expectBody();
 * 
 * logWebTestClientResponse(bodyContentSpec); return bodyContentSpec;
 * 
 * }
 * 
 * 
 * private WebTestClient.BodyContentSpec deleteAndVerifyPostsByUuid(List<String>
 * postsUuidList, HttpStatus expectedStatus) {
 * 
 * return client.delete() .uri(uriBuilder -> uriBuilder.path(CONTEXT_PATH +
 * "/posts/deleteByUuid") .queryParam("postUuid", postsUuidList) .build())
 * .accept(APPLICATION_JSON) .exchange()
 * .expectStatus().isEqualTo(expectedStatus) .expectBody(); }
 * 
 * 
 * private void logWebTestClientResponse(BodyContentSpec bodyContentSpec) {
 * 
 * String jsonResponse = new
 * String(bodyContentSpec.returnResult().getResponseBody());
 * 
 * ObjectMapper mapper = new ObjectMapper();
 * 
 * try { String prettyJsonResponse = mapper.writerWithDefaultPrettyPrinter()
 * .writeValueAsString(mapper.readTree(jsonResponse));
 * System.out.println("--------------------------------------------");
 * System.out.println("Response Body:"); System.out.println("---------------");
 * System.out.println(prettyJsonResponse);
 * System.out.println("--------------------------------------------");
 * 
 * 
 * } catch (JsonProcessingException e) {
 * 
 * e.printStackTrace(); }
 * 
 * }
 * 
 * 
 * }
 */