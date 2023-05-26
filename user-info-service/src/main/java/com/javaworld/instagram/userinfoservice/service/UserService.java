package com.javaworld.instagram.userinfoservice.service;

import java.util.UUID;

import com.javaworld.instagram.userinfoservice.service.dto.User;

public interface UserService {

	User createUser(User user);

	User findUser(UUID userUuid, int delay, int faultPercent);
	
	int deleteUser(UUID userUuid);
	
	

}
