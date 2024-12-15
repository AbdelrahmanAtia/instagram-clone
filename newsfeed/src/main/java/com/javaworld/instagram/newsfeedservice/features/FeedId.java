package com.javaworld.instagram.newsfeedservice.features;
import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FeedId implements Serializable {
	private UUID userId;
	private UUID postId;
}
