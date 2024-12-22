package com.javaworld.instagram.newsfeedservice.persistence.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class FeedId implements Serializable {

	@Column(name = "user_uuid")
	@Type(type = "org.hibernate.type.UUIDCharType")  // maps java UUID type to data base UUID type
	private UUID userUuid;

	@Column(name = "post_uuid")
	@Type(type = "org.hibernate.type.UUIDCharType")  // maps java UUID type to data base UUID type
	private UUID postUuid;
}
