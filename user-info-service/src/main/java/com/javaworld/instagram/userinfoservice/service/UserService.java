package com.javaworld.instagram.userinfoservice.service;

import java.util.List;
import java.util.UUID;

import com.javaworld.instagram.userinfoservice.service.dto.User;

public interface UserService {

	User createUser(User user);

	User findUser(UUID userUuid, int delay, int faultPercent);
	
	User partialUpdateUser(User updatedUserData);
	
	int deleteUser(UUID userUuid);
	
<<<<<<< HEAD
	List<User> getSuggestedUsers(String size);
	
=======
<<<<<<< Updated upstream
=======
	List<User> getSuggestedUsers(String size);
	
	void followUser(UUID fromUserId, UUID toUserId);
	
>>>>>>> Stashed changes
>>>>>>> temp
}
