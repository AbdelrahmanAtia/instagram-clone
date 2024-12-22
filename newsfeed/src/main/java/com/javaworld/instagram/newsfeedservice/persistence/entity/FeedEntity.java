package com.javaworld.instagram.newsfeedservice.persistence.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.javaworld.instagram.commonlib.persistence.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@Entity(name = "user_feed")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FeedEntity extends BaseEntity {

	@EmbeddedId
	private FeedId id;

}
