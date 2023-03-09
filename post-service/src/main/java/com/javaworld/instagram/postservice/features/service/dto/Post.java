package com.javaworld.instagram.postservice.features.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO: use lombok for getters & setters
public class Post {

	private String title;

	private UUID userUuid;
	
	private UUID postUuid;

	private List<Tag> tags = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UUID getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public UUID getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(UUID postUuid) {
		this.postUuid = postUuid;
	}	

}