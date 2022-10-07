package com.javaworld.instagram.postservice.features.persistence.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PostTagAssignmentId implements Serializable {

	@Column(name = "post_id")
	private int postId;

	@Column(name = "tag_id")
	private int tagId;

	private PostTagAssignmentId() {

	}

	public PostTagAssignmentId(int postId, int tagId) {
		this.postId = postId;
		this.tagId = tagId;
	}

	public int getPostId() {
		return postId;
	}

	public int getTagId() {
		return tagId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		PostTagAssignmentId that = (PostTagAssignmentId) o;
		return Objects.equals(postId, that.postId) && Objects.equals(tagId, that.tagId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postId, tagId);
	}
}