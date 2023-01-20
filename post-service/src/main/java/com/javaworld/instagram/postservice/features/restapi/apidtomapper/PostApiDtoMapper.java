package com.javaworld.instagram.postservice.features.restapi.apidtomapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.features.service.dto.Tag;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;
import com.javaworld.instagram.postservice.server.dto.TagApiDto;

@Mapper(componentModel = "spring")
public interface PostApiDtoMapper {

	@Mappings({ @Mapping(target = "tags", source = "tags") })
	Post apiToDto(PostApiDto apiDto);

	Tag apiToDto(TagApiDto apiDto);

	List<Tag> mapToDtoList(List<TagApiDto> apiDto);

    @Mapping(target = "postUuid", source = "")
	PostApiDto mapToApiDto(Post post);

	List<PostApiDto> mapToApiDto(List<Post> post);

}