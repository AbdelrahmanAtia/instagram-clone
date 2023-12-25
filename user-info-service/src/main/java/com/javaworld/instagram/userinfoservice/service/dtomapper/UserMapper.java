package com.javaworld.instagram.userinfoservice.service.dtomapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.javaworld.instagram.userinfoservice.commons.mapping.EntityMapper;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.service.dto.User;

@Mapper(componentModel = "spring", imports = {java.util.UUID.class})
public interface UserMapper extends EntityMapper<User, UserEntity> {

	@Override
	@Mapping(target = "userUuid", expression = "java(user.getUserUuid() == null ? UUID.randomUUID() : user.getUserUuid())")
	UserEntity toEntity(User user);	


}