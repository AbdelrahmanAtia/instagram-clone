package com.javaworld.instagram.postservice.features.post.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.postservice.commons.exceptions.InvalidInputException;
import com.javaworld.instagram.postservice.features.post.persistence.PostEntity;
import com.javaworld.instagram.postservice.features.post.service.PostService;
import com.javaworld.instagram.postservice.server.api.PostsApi;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;

@RestController
@RequestMapping("/api")
public class PostController implements PostsApi {

	@Autowired
	private PostApiDtoMapper postApiDtoMapper;

	@Autowired
	private PostService postService;

	@Override
	public ResponseEntity<List<PostApiDto>> getMyPosts() {

		return null;
	}

	@Override
	public ResponseEntity<PostApiDto> createPost(PostApiDto body) {

		try {
			
			PostEntity entity = postApiDtoMapper.apiToEntity(body);
			PostEntity newEntity = postService.createPost(entity);

			return ResponseEntity.ok(postApiDtoMapper.entityToApi(newEntity));
		} catch (DataIntegrityViolationException dive) {
			// TODO: return a specific error message here
			throw new InvalidInputException(dive.getMessage());
		}

	}

}
