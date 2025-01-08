package com.javaworld.instagram.newsfeedservice.dto;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Post {

	private String caption;
	private String fileName;
	private UUID userUuid;
	private UUID postUuid;

}
