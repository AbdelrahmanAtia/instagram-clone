package com.javaworld.instagram.newsfeedservice.service;

import java.util.List;

import com.javaworld.instagram.newsfeedservice.dto.Post;

public interface NewsfeedService {
		
	List<Post> getMyNewsfeed();

}
