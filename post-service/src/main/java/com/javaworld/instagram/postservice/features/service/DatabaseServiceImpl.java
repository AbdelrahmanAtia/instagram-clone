package com.javaworld.instagram.postservice.features.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.features.persistence.repositories.PostRepository;
import com.javaworld.instagram.postservice.features.persistence.repositories.TagRepository;

@Service
public class DatabaseServiceImpl {

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private PostRepository postRepository;

	@Transactional
	public void clearDatabase() {
		tagRepository.deleteAll();
		postRepository.deleteAll();
	}

}
