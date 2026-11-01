package com.javaworld.instagram.userinfoservice.service;

import com.javaworld.instagram.userinfoservice.service.dto.UserSearchKeyType;
import java.util.List;
import java.util.UUID;

import com.javaworld.instagram.userinfoservice.service.dto.User;

public interface UserService {

	User createUser(User user);

	User searchUser(String searchKeyValue, UserSearchKeyType searchKeyType);
	
	User partialUpdateUser(User updatedUserData);
	
	int deleteUser(UUID userUuid);
	
	List<User> getSuggestedUsers(String size);
	
	void followUser(UUID toUserId);
	
	void removeFollower(UUID followerId);
	
	List<User> getUserFollowers(UUID followedId);
	
	List<User> getUserFollowings(UUID followerId);

	void unfollow(UUID followedId);
	
	
	
	
	
	

	
}
