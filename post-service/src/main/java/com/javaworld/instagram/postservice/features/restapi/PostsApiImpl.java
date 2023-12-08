package com.javaworld.instagram.postservice.features.restapi;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.postservice.features.restapi.apidtomapper.PostApiDtoMapper;
import com.javaworld.instagram.postservice.features.service.PostService;
import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.server.api.PostsApi;
import com.javaworld.instagram.postservice.server.dto.DeletedPostsResponseApiDto;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;
import com.javaworld.instagram.postservice.server.dto.PostsCountResponseApiDto;

@RestController
public class PostsApiImpl implements PostsApi {

	private static final Logger logger = LoggerFactory.getLogger(PostsApiImpl.class);
	
	private final Random randomNumberGenerator = new Random();

	@Autowired
	private PostApiDtoMapper postApiDtoMapper;

	@Autowired
	private PostService postService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	
	// TODO: change the response to return a generic response saying that
	// the post is created successfully & change the api documentation response
	@Override
	public PostApiDto createPost(PostApiDto body) {
		try {

			Post post = postApiDtoMapper.apiToDto(body);
			
			Post savedPost = postService.createPost(post);
			return setServiceAddress(postApiDtoMapper.mapToApiDto(savedPost));

		} catch (DataIntegrityViolationException dive) {
			// TODO: return a specific error message here
			throw new InvalidInputException(dive.getMessage());
		}
	}
		
	@Override
	public List<PostApiDto> findPosts(UUID userUuid, Integer page) {
		Page<Post> posts = postService.getPosts(userUuid, page);
		List<PostApiDto> postsApiDtoList = postApiDtoMapper.mapToApiDto(posts.getContent());
		postsApiDtoList.forEach(e -> setServiceAddress(e));		
		return postsApiDtoList;
	}

	@Override
	public PostsCountResponseApiDto findPostsCount(UUID userUuid, Integer delay, Integer faultPercent) {
		
		logger.info("starting PostsApiImpl.findPostsCount()");
		
		throwErrorIfBadLuck(faultPercent); // used to test the retry mechanism..for example, if the faultPercent is 25 
		                                   // then for each 4 requests one of them will be forced to fail 
		                                   // so that the retry mechanism can be kicked off
		
		delay(delay); // used to test circuit breaker..the caller service will throw a timeout exception if 
		              // the request took more than 2 seconds to be fulfilled
		
		PostsCountResponseApiDto response = new PostsCountResponseApiDto();
		response.setPostsCount(postService.countPosts(userUuid));
		response.setServiceAddress(serviceUtil.getServiceAddress());
		return response;
	}
	
	@Override
	public DeletedPostsResponseApiDto deletePostsByUuuid(List<String> postUuids) {

		int deletedPostsCount = postService.deletePosts(postUuids);

		//TODO: Do we need a DTO for the service layer & a mapper
		return new DeletedPostsResponseApiDto().deletedPostsCount(deletedPostsCount)
				.message("Posts deleted successfully")
				.serviceAddress(serviceUtil.getServiceAddress());
	}
	
	private PostApiDto setServiceAddress(PostApiDto postApiDto) {
		postApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return postApiDto;
	}
	
	private void delay(int second) {
		try {
			logger.info("delaying for {} s..", second);
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			// Handle the exception if necessary
		}
	}
	
	private void throwErrorIfBadLuck(int faultPercent) {

		if (faultPercent == 0) {
			return;
		}

		int randomThreshold = getRandomNumber(1, 100);

		if (faultPercent < randomThreshold) {
			//TODO: change to debug
			logger.info("We got lucky, no error occurred, {} < {}", faultPercent, randomThreshold);
		} else {
			//TODO: change to debug
			logger.info("Bad luck, an error occurred, {} >= {}", faultPercent, randomThreshold);
			throw new RuntimeException("Something went wrong...");
		}
	}
	  
	private int getRandomNumber(int min, int max) {

		if (max < min) {
			throw new IllegalArgumentException("Max must be greater than min");
		}

		return randomNumberGenerator.nextInt((max - min) + 1) + min;
	}

}