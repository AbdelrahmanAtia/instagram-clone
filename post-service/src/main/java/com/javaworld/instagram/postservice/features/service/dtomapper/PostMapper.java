package com.javaworld.instagram.postservice.features.service.dtomapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;
import com.javaworld.instagram.postservice.features.persistence.entities.PostTagAssignment;
import com.javaworld.instagram.postservice.features.persistence.entities.TagEntity;
import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.features.service.dto.Tag;

@Mapper(componentModel = "spring", imports = {java.util.UUID.class})
public interface PostMapper {

    @Mapping(target = "postUuid", expression = "java(UUID.randomUUID())")
	PostEntity dtoToEntity(Post post);

	TagEntity dtoToEntity(Tag tag);

	List<TagEntity> dtoListToEntityList(List<Tag> tag);

	@Mapping(target = "tags", expression = "java(mapToTags(entity.getPostTagAssignmentList()))")
	Post entityToDto(PostEntity entity);

	List<Post> entityListToDtoList(List<PostEntity> entityList);
	
	Tag mapTagEntityToDto(TagEntity entity);
	
	List<Tag> mapTagEntityListToDtoList(List<TagEntity> entity);
	
	
	default List<Tag> mapToTags(List<PostTagAssignment> postTagAssignmentList) {

		List<Tag> tagsList = new ArrayList<>();
		
		postTagAssignmentList.forEach(e -> {
			Tag tag = mapTagEntityToDto(e.getTag());
			tagsList.add(tag);
		});
		
		return tagsList;
	}


}