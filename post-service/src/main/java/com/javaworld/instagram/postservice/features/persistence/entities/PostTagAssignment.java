package com.javaworld.instagram.postservice.features.persistence.entities;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity(name = "PostTag") // TODO: why provide entity name? what is it used for ?
@Table(name = "post_tag")
public class PostTagAssignment {

	@EmbeddedId
	private PostTagAssignmentId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("postId")
	private PostEntity postEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("tagId")
	private TagEntity tagEntity;

	@Column(name = "created_on")
	private Date createdOn = new Date();

	private PostTagAssignment() {

	}

	public PostTagAssignment(PostEntity postEntity, TagEntity tagEntity) {
		this.postEntity = postEntity;
		this.tagEntity = tagEntity;
		this.id = new PostTagAssignmentId(postEntity.getId(), tagEntity.getId());
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		PostTagAssignment that = (PostTagAssignment) o;
		return Objects.equals(postEntity, that.postEntity) && Objects.equals(tagEntity, that.tagEntity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(postEntity, tagEntity);
	}
}