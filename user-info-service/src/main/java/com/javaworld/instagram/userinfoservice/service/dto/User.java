package com.javaworld.instagram.userinfoservice.service.dto;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

	private String mobileNumber;

	private String email;

	private String fullName;

	private String username;

	private String password;

	private UUID userUuid;

	private int postsCount;
	
	private int followersCount;

	private int followingCount;

	private String profileImageName;


}
