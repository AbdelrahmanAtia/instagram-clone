package com.javaworld.instagram.postservice.features.post.restapi;

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

	@Autowired
	private PostApiDtoMapper postApiDtoMapper;

	@Autowired
	private PostService postService;
	
	@Autowired
	private ServiceUtil serviceUtil;

	@Override
	public Mono<ResponseEntity<Flux<PostApiDto>>> getMyPosts(ServerWebExchange exchange) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ResponseEntity<PostApiDto>> createPost(Mono<PostApiDto> body, ServerWebExchange exchange) {
	try {
			
			PostEntity entity = postApiDtoMapper.apiToEntity(body.block());
			
			return postService.createPost(entity)  
			           .map(e -> postApiDtoMapper.entityToApi(e))
			           .map(e -> setServiceAddress(e))
			           .map(e -> ResponseEntity.ok(e));
						
		} catch (DataIntegrityViolationException dive) {
			// TODO: return a specific error message here
			throw new InvalidInputException(dive.getMessage());
		}
	}
	
	
	private PostApiDto setServiceAddress(PostApiDto postApiDto) {
		postApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return postApiDto;
	}

}
