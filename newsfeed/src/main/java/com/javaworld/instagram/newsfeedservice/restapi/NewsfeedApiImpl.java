package com.javaworld.instagram.newsfeedservice.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.newsfeedservice.persistence.repository.FeedRepository;
import com.javaworld.instagram.newsfeedservice.server.api.NewsfeedApi;
import com.javaworld.instagram.newsfeedservice.server.dto.PostApiDto;
import com.javaworld.instagram.newsfeedservice.service.NewsfeedService;

@RestController

@RequestMapping("/feed")
public class NewsfeedApiImpl implements NewsfeedApi {

	@Autowired
	private NewsfeedService newsfeedService;

	@Override
	public List<PostApiDto> retrieveNewsFeed() {

		newsfeedService.getMyNewsfeed();
		return null;
	}
}
