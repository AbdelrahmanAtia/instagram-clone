package com.javaworld.instagram.postservice.features.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.postservice.features.restapi.apidto.PostApiDto;
import com.javaworld.instagram.postservice.features.restapi.apidto.PostsCountResponseApiDto;
import com.javaworld.instagram.postservice.features.restapi.apidtomapper.PostApiDtoMapper;
import com.javaworld.instagram.postservice.features.service.PostService;
import com.javaworld.instagram.postservice.features.service.dto.Post;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PostsApiImpl implements PostsApi {

	private static final Logger logger = LoggerFactory.getLogger(PostsApiImpl.class);

	@Autowired
	private PostApiDtoMapper postApiDtoMapper;

	@Autowired
	private PostService postService;
	
	@Autowired
	private ServiceUtil serviceUtil;
	
	
	// TODO: what is the use of ServerWebExchange
	// TODO: change the response to return a generic response saying that
	// the post is created successfully & change the api documentation response
	@Override
	public Mono<Void> createPost(PostApiDto body, ServerWebExchange exchange) {
		try {

			Post post = postApiDtoMapper.apiToDto(body);
			return postService.createPost(post);

		} catch (DataIntegrityViolationException dive) {
			// TODO: return a specific error message here
			throw new InvalidInputException(dive.getMessage());
		}
	}
		
    //TODO: what is the use of ServerWebExchange
	@Override
	public Flux<PostApiDto> findPostsByUserId(Integer userId, ServerWebExchange exchange) {
		
		return postService.getPosts(userId)
				.map(e -> postApiDtoMapper.mapToApiDto(e))
				.map(e -> setServiceAddress(e));
	}
	
    //TODO: what is the use of ServerWebExchange
	@Override
	public Mono<Void> deletePostsByUserId(Integer userId, ServerWebExchange exchange) {
		
		return postService.deletePosts(userId);
	}
	
	@Override
	public Mono<PostsCountResponseApiDto> findPostsCount(Integer userId, ServerWebExchange exchange) {
		// TODO: to be implemented..
		return null;
	}
	
	private PostApiDto setServiceAddress(PostApiDto postApiDto) {
		postApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return postApiDto;
	}
		
}