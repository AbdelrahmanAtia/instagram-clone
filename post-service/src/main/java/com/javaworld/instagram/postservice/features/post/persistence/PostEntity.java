package com.javaworld.instagram.postservice.features.post.persistence;

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
import javax.persistence.Version;

@Entity
@Table(name = "post", indexes = { @Index(name = "posts_unique_idx", unique = true, columnList = "postUuid") })
public class PostEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Version
	private int version;

	private UUID postUuid;

	// TODO: change to caption
	private String title;

	private int userId;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentEntity> comments = new ArrayList<>();

	public PostEntity() {

	}

	public PostEntity(UUID postUuid, String title, int userId) {
		this.postUuid = postUuid;
		this.title = title;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public List<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(List<CommentEntity> comments) {
		this.comments = comments;
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

	public UUID getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(UUID postUuid) {
		this.postUuid = postUuid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PostEntity [id=");
		builder.append(id);
		builder.append(", version=");
		builder.append(version);
		builder.append(", postUuid=");
		builder.append(postUuid);
		builder.append(", title=");
		builder.append(title);
		builder.append(", userId=");
		builder.append(userId);
		builder.append("]");
		return builder.toString();
	}

}
