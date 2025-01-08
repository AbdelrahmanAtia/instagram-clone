package com.javaworld.instagram.newsfeedservice.integration;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.newsfeedservice.dto.Post;
import com.javaworld.instagram.newsfeedservice.service.dtomapper.PostMapper;
import com.javaworld.instagram.postservice.server.api.PostApi;
import com.javaworld.instagram.postservice.server.dto.GetPostsRequestApiDto;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;

@Service
public class PostIntegrationServiceImpl implements PostIntegrationService {

	private static final Logger logger = LoggerFactory.getLogger(PostIntegrationServiceImpl.class);

	@Autowired
	private PostApi postApi;
	
	@Autowired
	private PostMapper postMapper;

	@Override
	@Transactional(readOnly = true)
	public List<Post> getPosts(List<UUID> postsIds) {
		logger.info("starting PostIntegrationServiceImpl.getPosts()");
		
		GetPostsRequestApiDto getPostsRequest = new GetPostsRequestApiDto();
		getPostsRequest.setPostsIds(postsIds);

		List<PostApiDto> postApiDtoList = postApi.retrievePosts(getPostsRequest);

		return postMapper.toDtoList(postApiDtoList);

	}

}
