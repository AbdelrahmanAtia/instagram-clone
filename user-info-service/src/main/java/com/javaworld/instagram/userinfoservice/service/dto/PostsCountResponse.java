package com.javaworld.instagram.userinfoservice.service.dto;

public class PostsCountResponse {

	private int postsCount;

	public PostsCountResponse(int postsCount) {
		this.postsCount = postsCount;
	}

	public int getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(int postsCount) {
		this.postsCount = postsCount;
	}

}
