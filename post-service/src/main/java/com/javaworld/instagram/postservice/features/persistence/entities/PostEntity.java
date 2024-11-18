package com.javaworld.instagram.postservice.features.persistence.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.javaworld.instagram.commonlib.persistence.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post", indexes = { @Index(name = "posts_unique_idx", unique = true, columnList = "postUuid") })
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Type(type = "org.hibernate.type.UUIDCharType") // maps java UUID type to data base UUID type
	private UUID postUuid;

	// TODO: change to caption
	private String caption;
	
	private String fileName;

	@Type(type = "org.hibernate.type.UUIDCharType") // maps java UUID type to data base UUID type
	private UUID userUuid;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentEntity> comments = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PostTagAssignment> postTagAssignmentList = new ArrayList<>();


	public PostEntity(UUID postUuid, String caption, UUID userUuid) {
		this.postUuid = postUuid;
		this.caption = caption;
		this.userUuid = userUuid;
	}

	// helper methods
	public void addComment(CommentEntity comment) {
		comments.add(comment);
		comment.setPost(this);
	}

	public void removeComment(CommentEntity comment) {
		comments.remove(comment);
		comment.setPost(null);
	}

	public void addPostTagAssignment(TagEntity tagEntity) {
		PostTagAssignment postTagAssignment = new PostTagAssignment(this, tagEntity);
		postTagAssignmentList.add(postTagAssignment);
		// tagEntity.getPostTagAssignmentList().add(postTagAssignment);
	}

}
