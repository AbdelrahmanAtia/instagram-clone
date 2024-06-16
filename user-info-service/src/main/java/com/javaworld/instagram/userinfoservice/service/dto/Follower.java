package com.javaworld.instagram.userinfoservice.service.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Follower {

	private UUID userUuid;
	
	private String profileImageName;
	
	private String fullName;

	private String username;

}
