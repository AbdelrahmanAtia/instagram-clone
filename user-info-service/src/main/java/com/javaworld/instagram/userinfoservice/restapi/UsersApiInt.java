package com.javaworld.instagram.userinfoservice.restapi;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.userinfoservice.service.UserService;

@RestController
@RequestMapping("/int")
public class UsersApiInt {

	private static final Logger logger = LoggerFactory.getLogger(UsersApiInt.class);

	@Autowired
	private UserService userService;

	@GetMapping("/users/{userUuid}/followersIds")
	public List<UUID> getUserFollowersIds(@PathVariable("userUuid") UUID userUuid) {
		logger.info("retrieving followers ids of user with uuid {}", userUuid);
		return userService.getUserFollowersIds(userUuid);
	}
}
