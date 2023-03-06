package com.javaworld.instagram.userinfoservice.service.dtomapper;

import org.mapstruct.Mapper;

import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.service.dto.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserEntity dtoToEntity(User user);
	
	User mapUserEntityToDto(UserEntity userEntity);


}