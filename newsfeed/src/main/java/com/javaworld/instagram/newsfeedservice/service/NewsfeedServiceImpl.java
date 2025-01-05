package com.javaworld.instagram.newsfeedservice.service;

import java.util.List;
import java.util.UUID;

import org.openapitools.api.PostsApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.commonlib.security.SecurityUtil;
import com.javaworld.instagram.newsfeedservice.persistence.repository.FeedRepository;

@Service
public class NewsfeedServiceImpl implements NewsfeedService {

	private static final Logger logger = LoggerFactory.getLogger(NewsfeedServiceImpl.class);

	@Autowired
	private FeedRepository feedRepository;
	
	@Autowired
	private PostsApi postsApi;

	@Override
	@Transactional(readOnly = true)
	public List<UUID> getMyNewsfeed() {
		
		UUID loggedInUserId = SecurityUtil.getUserUuidFromAccessToken(SecurityContextHolder.getContext());
		
		logger.info("retrieving feed for user id: {}", loggedInUserId);
		
		List<UUID> postsIds = feedRepository.findPostIdsByUserId(loggedInUserId);
		
		//TODO: create a new api in posts-ms to get posts list by id
		//TODO: use postsApi to call this newly created api
				
		return null;
	}

}
