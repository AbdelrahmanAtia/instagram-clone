package com.javaworld.instagram.postservice.features.restapi;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
	public List<PostApiDto> findPosts(UUID userUuid) {
		List<Post> posts = postService.getPosts(userUuid);
		List<PostApiDto> postsApiDtoList = postApiDtoMapper.mapToApiDto(posts);
		postsApiDtoList.forEach(e -> setServiceAddress(e));
		return postsApiDtoList;
	}

	@Override
	public PostsCountResponseApiDto findPostsCount(UUID userUuid, Integer delay, Integer faultPercent) {
		
		logger.info("starting PostsApiImpl.findPostsCount()");
		
		delay(delay);
		
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

}