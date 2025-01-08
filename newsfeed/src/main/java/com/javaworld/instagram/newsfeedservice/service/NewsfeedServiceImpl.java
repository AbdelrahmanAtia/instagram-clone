package com.javaworld.instagram.newsfeedservice.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.commonlib.security.SecurityUtil;
import com.javaworld.instagram.newsfeedservice.dto.Post;
import com.javaworld.instagram.newsfeedservice.integration.PostIntegrationService;
import com.javaworld.instagram.newsfeedservice.persistence.repository.FeedRepository;

@Service
public class NewsfeedServiceImpl implements NewsfeedService {

	private static final Logger logger = LoggerFactory.getLogger(NewsfeedServiceImpl.class);

	@Autowired
	private FeedRepository feedRepository;

	@Autowired
	private PostIntegrationService postIntegrationService;

	@Override
	@Transactional(readOnly = true)
	public List<Post> getMyNewsfeed() {

		UUID loggedInUserId = SecurityUtil.getUserUuidFromAccessToken(SecurityContextHolder.getContext());

		logger.info("retrieving feed for user id: {}", loggedInUserId);

		List<UUID> postsIds = feedRepository.findByIdUserUuid(loggedInUserId);

		return postIntegrationService.getPosts(postsIds);
	}

}
