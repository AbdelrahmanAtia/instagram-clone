package com.javaworld.instagram.userinfoservice.service.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {

	private String mobileNumber;

	private String email;

	private String fullName;

	private String username;

	private String password;

	private UUID userUuid;

	private int postsCount;
	
	private String profileImageName;


}
