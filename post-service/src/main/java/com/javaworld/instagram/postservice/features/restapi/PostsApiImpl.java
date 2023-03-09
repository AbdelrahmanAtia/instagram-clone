package com.javaworld.instagram.postservice.features.restapi;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
	public PostsCountResponseApiDto findPostsCount(@NotNull @Valid UUID userUuid) {
		PostsCountResponseApiDto response = new PostsCountResponseApiDto();
		response.setPostsCount(4);
		response.setServiceAddress(serviceUtil.getServiceAddress());
		return response;
	}

	
	
	@Override
	public Void deletePostsByUuuid(List<String> postUuids) {

		postService.deletePosts(postUuids);
		
		return null; //TODO: change return type to void and check how can 
		              //open-api also generate void instead of Void
		              //then remove this return statement
	}	
	
	private PostApiDto setServiceAddress(PostApiDto postApiDto) {
		postApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return postApiDto;
	}

}