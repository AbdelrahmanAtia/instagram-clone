package com.javaworld.instagram.postservice.features.service.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

	private String title;

	private String fileName;

	private UUID userUuid;

	private UUID postUuid;

	private List<Tag> tags = new ArrayList<>();

}