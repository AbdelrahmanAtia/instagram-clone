package com.javaworld.instagram.userinfoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.userinfoservice.persistence.FollowerRepository;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;

@Service
public class DatabaseServiceImpl {

	@Autowired
	private FollowerRepository followerRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public void clearDatabase() {
		followerRepository.deleteAll();
		userRepository.deleteAll();
	}

}
