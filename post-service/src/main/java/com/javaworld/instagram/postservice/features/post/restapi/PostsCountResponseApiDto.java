package com.javaworld.instagram.postservice.features.post.restapi;

public class PostsCountResponseApiDto {

	private Integer postsCount;

	private String serviceAddress;

	public Integer getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(Integer postsCount) {
		this.postsCount = postsCount;
	}

	public String getServiceAddress() {
		return serviceAddress;
	}

	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}

}
