package com.javaworld.instagram.userinfoservice.restapi;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.javaworld.instagram.userinfoservice.persistence.UserEntity;


@Mapper(componentModel = "spring")
public interface UserApiDtoMapper {

	@Mappings({
		//TODO: add service address to the response and uncomment the following mapping
		// @Mapping(target = "serviceAddress", ignore = true)
	})
	UserApiDto entityToApi(UserEntity entity);

	@Mappings({
		//@Mapping(target = "id", ignore = true), //identity key for user entity is the username 
		@Mapping(target = "version", ignore = true) 
	})
	UserEntity apiToEntity(UserApiDto apiDto);

}