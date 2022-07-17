package com.javaworld.userinfoservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.userinfoservice.model.User;

@RestController
@RequestMapping("/api")
public class UserInfoController {
	
	@GetMapping("users/me/followers")
	public List<User> getCurrentUserFollowers() {
		
		User user1 = new User();
		user1.setUsername("vlad");
		
		User user2 = new User();
		user2.setUsername("youssef");
		
		return Arrays.asList(user1, user2);
	}

}
