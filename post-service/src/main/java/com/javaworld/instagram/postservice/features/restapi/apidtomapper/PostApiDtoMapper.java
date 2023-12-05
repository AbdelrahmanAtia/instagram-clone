package com.javaworld.instagram.postservice.features.restapi.apidtomapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.javaworld.instagram.postservice.features.service.dto.Post;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;

@Mapper(componentModel = "spring")
public interface PostApiDtoMapper {

	Post apiToDto(PostApiDto apiDto);

    @Mapping(target = "postUuid", source = "")
	PostApiDto mapToApiDto(Post post);

	List<PostApiDto> mapToApiDto(List<Post> post);

}