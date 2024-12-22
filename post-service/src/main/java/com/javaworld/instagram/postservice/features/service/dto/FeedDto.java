package com.javaworld.instagram.postservice.features.service.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class FeedDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID userUuid;
	
	private UUID postUuid;

}