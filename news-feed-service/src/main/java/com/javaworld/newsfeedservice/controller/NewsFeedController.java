package com.javaworld.newsfeedservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.javaworld.newsfeedservice.model.Post;
import com.javaworld.newsfeedservice.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping("/api")
public class NewsFeedController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/newsfeed")
	@HystrixCommand(fallbackMethod = "getFallbackNewsFeed")
	public List<Post> getNewsFeed() {

		// get current user followers
		User[] followers = restTemplate.getForObject("http://user-info-service/api/users/me/followers", User[].class);
		System.out.println("current user followers: " + followers);

		// get [latest] first x posts of each follower
		List<Post> newsFeedPosts = new ArrayList<>();
		Arrays.asList(followers).forEach(follower -> {
			Post [] posts = restTemplate.getForObject("http://post-service/api/users/hasan/posts", Post[].class);
			newsFeedPosts.addAll(Arrays.asList(posts));
		});

		return newsFeedPosts;	
	}
	
	
	
	private List<Post> getFallbackNewsFeed() {
		//return an old feedback or use any other way for resiliency
		
		Post p1 = new Post();
		p1.setId(0L);
		p1.setTitle("this a first fallback post");
		
		Post p2 = new Post();
		p2.setId(1L);
		p2.setTitle("this a second fallback post");
		
		return Arrays.asList(p1, p2);
	}
	
	
	
	

}
