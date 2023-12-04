package com.javaworld.instagram.postservice.features.service.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

	private String caption;

	private String fileName;

	private UUID userUuid;

	private UUID postUuid;

}