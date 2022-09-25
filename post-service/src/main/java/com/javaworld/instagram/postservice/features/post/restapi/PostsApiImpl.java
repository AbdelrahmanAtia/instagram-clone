package com.javaworld.instagram.postservice.features.post.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.postservice.features.post.persistence.PostEntity;
import com.javaworld.instagram.postservice.features.post.service.PostService;

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
	
	
    //TODO: what is the use of ServerWebExchange
	@Override
	public Mono<PostApiDto> createPost(PostApiDto body, ServerWebExchange exchange) {
	try {
		
			PostEntity entity = postApiDtoMapper.apiToEntity(body);
			
			return postService.createPost(entity)  
			           .map(e -> postApiDtoMapper.entityToApi(e))
			           .map(e -> setServiceAddress(e));
			           
		} catch (DataIntegrityViolationException dive) {
			// TODO: return a specific error message here
			throw new InvalidInputException(dive.getMessage());
		}
	}
		
    //TODO: what is the use of ServerWebExchange
	@Override
	public Flux<PostApiDto> findPostsByUserId(Integer userId, ServerWebExchange exchange) {

		return postService.getPosts(userId)
				.map(e -> postApiDtoMapper.entityToApi(e))
				.map(e -> setServiceAddress(e));
	}
	
    //TODO: what is the use of ServerWebExchange
	@Override
	public Mono<Void> deletePostsByUserId(Integer userId, ServerWebExchange exchange) {
		
		return postService.deletePosts(userId);
	}
	
	private PostApiDto setServiceAddress(PostApiDto postApiDto) {
		postApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return postApiDto;
	}
		
}