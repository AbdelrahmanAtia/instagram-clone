package com.javaworld.instagram.postservice.features.post;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {

	@Id
	private Long id;

	private String title;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostComment> comments = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	

	public Post() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<PostComment> getComments() {
		return comments;
	}

	public void setComments(List<PostComment> comments) {
		this.comments = comments;
	}

	// helper methods
	public void addComment(PostComment comment) {
		comments.add(comment);
		comment.setPost(this);
	}

	public void removeComment(PostComment comment) {
		comments.remove(comment);
		comment.setPost(null);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
