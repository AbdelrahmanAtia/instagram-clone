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

    private UUID userUuid;
    private String mobileNumber;
	private String email;
	private String fullName;
	private String username;
	private String password;
    private long followersCount;
    private long followingsCount;
    private int postsCount;
    private String profileImageName;
}
