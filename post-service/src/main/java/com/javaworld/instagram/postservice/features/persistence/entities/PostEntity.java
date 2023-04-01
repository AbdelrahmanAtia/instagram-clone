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
import com.javaworld.instagram.postservice.commons.entities.BaseEntity;

@Entity
@Table(name = "post", indexes = { @Index(name = "posts_unique_idx", unique = true, columnList = "postUuid") })
public class PostEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Type(type="org.hibernate.type.UUIDCharType")  //maps java UUID type to data base UUID type
	private UUID postUuid;

	// TODO: change to caption
	private String title;

	@Type(type="org.hibernate.type.UUIDCharType")  //maps java UUID type to data base UUID type
	private UUID userUuid;

	@OneToMany(
		mappedBy = "post", 
		cascade = CascadeType.ALL, 
		orphanRemoval = true)
	private List<CommentEntity> comments = new ArrayList<>();

	@OneToMany(
		mappedBy = "post", 
		cascade = CascadeType.ALL, 
		orphanRemoval = true)
	private List<PostTagAssignment> postTagAssignmentList = new ArrayList<>();
	
	public PostEntity() {

	}

	public PostEntity(UUID postUuid, String title, UUID userUuid) {
		this.postUuid = postUuid;
		this.title = title;
		this.userUuid = userUuid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UUID getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(UUID userUuid) {
		this.userUuid = userUuid;
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

	public List<PostTagAssignment> getPostTagAssignmentList() {
		return postTagAssignmentList;
	}

	public void setPostTagAssignmentList(List<PostTagAssignment> postTagAssignmentList) {
		this.postTagAssignmentList = postTagAssignmentList;
	}
	
    public void addPostTagAssignment(TagEntity tagEntity) {
    	PostTagAssignment postTagAssignment = new PostTagAssignment(this, tagEntity);
        postTagAssignmentList.add(postTagAssignment);
        //tagEntity.getPostTagAssignmentList().add(postTagAssignment);
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
		builder.append(", postUuid=");
		builder.append(postUuid);
		builder.append(", title=");
		builder.append(title);
		builder.append(", userId=");
		builder.append(userUuid);
		builder.append(", postTagAssignmentList=");
		builder.append(postTagAssignmentList);
		builder.append("]");
		return builder.toString();
	}

}
