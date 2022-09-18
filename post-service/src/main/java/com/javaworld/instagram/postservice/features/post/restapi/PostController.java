package com.javaworld.instagram.postservice.features.post.restapi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.postservice.features.post.persistence.PostEntity;
import com.javaworld.instagram.postservice.features.post.service.PostService;
import com.javaworld.instagram.postservice.server.api.PostsApi;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PostController implements PostsApi {

	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostApiDtoMapper postApiDtoMapper;

	@Autowired
	private PostService postService;
	
	@Autowired
	private ServiceUtil serviceUtil;

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
		
	@Override
	public Mono<Flux<PostApiDto>> findPostsByUserId(Integer userId, ServerWebExchange exchange) {
		
		Flux<PostApiDto> postApiDtoFlux = postService.getPosts(userId)
		           .map(e -> postApiDtoMapper.entityToApi(e))
		           .map(e -> setServiceAddress(e));
	
		return postApiDtoFlux; //TODO: must be wrapped with Mono
	}
	
	private PostApiDto setServiceAddress(PostApiDto postApiDto) {
		postApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return postApiDto;
	}

}