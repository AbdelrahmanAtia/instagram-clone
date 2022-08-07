package com.javaworld.instagram.postservice.features.post.service;

import com.javaworld.instagram.postservice.features.post.persistence.PostEntity;

public interface PostService {

	PostEntity createPost(PostEntity postEntity);

}
