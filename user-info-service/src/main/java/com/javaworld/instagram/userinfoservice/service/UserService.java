package com.javaworld.instagram.userinfoservice.service;

import java.util.List;
import java.util.UUID;

import com.javaworld.instagram.userinfoservice.service.dto.User;

public interface UserService {

	User findUser(UUID userUuid, int delay, int faultPercent);
	
	List<User> getUserFollowers(UUID followedId);
	
	List<User> getUserFollowings(UUID followerId);
	
	List<User> getSuggestedUsers(String size);
	
	List<UUID> getUserFollowersIds(UUID followedId);
	
	User createUser(User user);
	
	User partialUpdateUser(User updatedUserData);
	
	int deleteUser(UUID userUuid);
	
	void followUser(UUID toUserId);
	
	void removeFollower(UUID followerId);

	void unfollow(UUID followedId);

	
}
