package com.javaworld.instagram.postservice.features.persistence.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "post_tag_assignment")
//TODO: use lombok
public class PostTagAssignment {

	//TODO: use an embedded id consisting of (postId & tagId) and add them to an embeddable class
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private PostEntity post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private TagEntity tag;

	@Column(name = "created_on")
	private Date createdOn = new Date();

	public PostTagAssignment() {

	}

	public PostTagAssignment(PostEntity post, TagEntity tag) {
		this.post = post;
		this.tag = tag;
	}	

	public PostEntity getPost() {
		return post;
	}

	public void setPost(PostEntity post) {
		this.post = post;
	}

	public TagEntity getTag() {
		return tag;
	}

	public void setTag(TagEntity tag) {
		this.tag = tag;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		PostTagAssignment that = (PostTagAssignment) o;
		return Objects.equals(post, that.post) && Objects.equals(tag, that.tag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(post, tag);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostTagAssignment [tag=");
		builder.append(tag);
		builder.append("]");
		return builder.toString();
	}
	
}