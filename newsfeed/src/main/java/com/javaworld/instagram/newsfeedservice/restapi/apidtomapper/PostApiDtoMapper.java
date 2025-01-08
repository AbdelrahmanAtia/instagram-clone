package com.javaworld.instagram.newsfeedservice.restapi.apidtomapper;

import org.mapstruct.Mapper;

import com.javaworld.instagram.commonlib.mapper.ApiDtoMapper;
import com.javaworld.instagram.newsfeedservice.dto.Post;
import com.javaworld.instagram.newsfeedservice.server.dto.PostApiDto;

@Mapper(componentModel = "spring")
public interface PostApiDtoMapper extends ApiDtoMapper<PostApiDto, Post> {

}
