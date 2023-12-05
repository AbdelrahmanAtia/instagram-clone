package com.javaworld.instagram.postservice.features.service.dtomapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;
import com.javaworld.instagram.postservice.features.service.dto.Post;

@Mapper(componentModel = "spring", imports = { java.util.UUID.class })
public interface PostMapper {

	@Mapping(target = "postUuid", expression = "java(post.getPostUuid() == null ? UUID.randomUUID() : post.getPostUuid())")
	PostEntity dtoToEntity(Post post);

	Post entityToDto(PostEntity entity);

	List<Post> entityListToDtoList(List<PostEntity> entityList);

	
	default List<UUID> mapStrUuidToUuidObj(List<String> uuids) {
		return uuids.stream().map(i -> UUID.fromString(i)).collect(Collectors.toList());
	}
	


}