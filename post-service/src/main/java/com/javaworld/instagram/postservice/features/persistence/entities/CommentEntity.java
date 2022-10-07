package com.javaworld.instagram.postservice.features.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post_comment")
public class CommentEntity {

	@Id
	private Long id;

	private String reply;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private PostEntity post;

	public CommentEntity() {

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

	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}
	
	

}
