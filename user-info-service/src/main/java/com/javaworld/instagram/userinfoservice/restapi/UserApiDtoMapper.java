package com.javaworld.instagram.userinfoservice.restapi;

import org.mapstruct.Mapper;

import com.javaworld.instagram.userinfoservice.server.dto.CreateUserRequestApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.UserApiDto;
import com.javaworld.instagram.userinfoservice.service.dto.User;

@Mapper(componentModel = "spring")
public interface UserApiDtoMapper {

	UserApiDto mapToUserApiDto(User user);

	User mapCreateUserRequestToUserDto(CreateUserRequestApiDto apiDto);

}