package com.javaworld.instagram.userinfoservice.service.dtomapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.javaworld.instagram.userinfoservice.commons.mapping.EntityMapper;
import com.javaworld.instagram.userinfoservice.persistence.FollowerEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.service.dto.User;

@Mapper(componentModel = "spring", imports = {java.util.UUID.class})
public interface UserMapper extends EntityMapper<User, UserEntity> {

	@Override
	@Mapping(target = "userUuid", expression = "java(user.getUserUuid() == null ? UUID.randomUUID() : user.getUserUuid())")
	UserEntity toEntity(User user);	
	
	
	/*
	@Mapping(target = "userUuid", source = "followerEntity.follower.userUuid")
	@Mapping(target = "profileImageName", source = "followerEntity.follower.profileImageName")
	@Mapping(target = "username", source = "followerEntity.follower.username")
	@Mapping(target = "fullName", source = "followerEntity.follower.fullName")
	User toUserDto(FollowerEntity followerEntity);
	*/

	List<User> toUserDtoList(List<UserEntity> followerEntityList);
	

}