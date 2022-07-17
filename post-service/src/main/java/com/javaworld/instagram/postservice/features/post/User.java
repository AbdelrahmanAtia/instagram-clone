package com.javaworld.instagram.postservice.features.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	private String username;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();

	public User() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	// helper methods
	public void addPost(Post post) {
		posts.add(post);
		post.setUser(this);
	}

	public void removePost(Post post) {
		posts.remove(post);
		post.setUser(null);
	}

}
