package com.javaworld.instagram.newsfeedservice.restapi;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.newsfeedservice.dto.Post;
import com.javaworld.instagram.newsfeedservice.restapi.apidtomapper.PostApiDtoMapper;
import com.javaworld.instagram.newsfeedservice.server.api.NewsfeedApi;
import com.javaworld.instagram.newsfeedservice.server.dto.PostApiDto;
import com.javaworld.instagram.newsfeedservice.service.NewsfeedService;

@RestController
public class NewsfeedApiImpl implements NewsfeedApi {

	private static final Logger logger = LoggerFactory.getLogger(NewsfeedApiImpl.class);

	@Autowired
	private NewsfeedService newsfeedService;

	@Autowired
	private PostApiDtoMapper postApiDtoMapper;

	@Override
	public List<PostApiDto> retrieveNewsFeed() {
		logger.info("starting rest request to get user newsfeed");
		List<Post> posts = newsfeedService.getMyNewsfeed();
		List<PostApiDto> postApiDtoList = postApiDtoMapper.toApiDtoList(posts);
		return postApiDtoList;
	}
}
