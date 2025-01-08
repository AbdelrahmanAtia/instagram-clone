package com.javaworld.instagram.newsfeedservice.service.dtomapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.javaworld.instagram.newsfeedservice.dto.Post;
import com.javaworld.instagram.postservice.server.dto.PostApiDto;

@Mapper(componentModel = "spring")
public interface PostMapper {

	Post toDto(PostApiDto apiDto);

	List<Post> toDtoList(List<PostApiDto> apiDtoList);

}
