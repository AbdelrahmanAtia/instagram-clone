package com.javaworld.instagram.newsfeedservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.newsfeedservice.persistence.repository.FeedRepository;

@Service
public class DatabaseServiceImpl {

	@Autowired
	private FeedRepository feedRepository;

	@Transactional
	public void clearDatabase() {
		feedRepository.deleteAll();
	}

}
