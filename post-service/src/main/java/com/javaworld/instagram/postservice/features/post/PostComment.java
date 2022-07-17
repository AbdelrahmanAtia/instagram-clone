package com.javaworld.instagram.postservice.features.post;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post_comment")
public class PostComment {

	@Id
	private Long id;

	private String reply;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;

	public PostComment() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	

}
