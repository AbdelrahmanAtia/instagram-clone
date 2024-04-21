package com.javaworld.instagram.userinfoservice.restapi;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.userinfoservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.userinfoservice.server.dto.PartialUpdateUserRequestApiDto;

import com.javaworld.instagram.userinfoservice.server.api.UsersApi;
import com.javaworld.instagram.userinfoservice.server.dto.CreateUserRequestApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.DeletedUsersResponseApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.UserApiDto;
import com.javaworld.instagram.userinfoservice.service.UserService;
import com.javaworld.instagram.userinfoservice.service.dto.User;

@RestController
public class UsersApiImpl implements UsersApi {

	private static final Logger logger = LoggerFactory.getLogger(UsersApiImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserApiDtoMapper mapper;

	@Autowired
	private ServiceUtil serviceUtil;

	@Override
	public UserApiDto createUser(CreateUserRequestApiDto createUserRequestApiDto) {
		User savedUser = userService.createUser(mapper.mapCreateUserRequestToUserDto(createUserRequestApiDto));
		UserApiDto response = mapper.toApiDto(savedUser);
		return setServiceAddress(response);
	}

	@Override
	public UserApiDto findUser(UUID userUuid, Integer delay, Integer faultPercent) { 
		UserApiDto userApiDto = mapper.toApiDto(userService.findUser(userUuid, delay, faultPercent));
		return setServiceAddress(userApiDto);
	}
	
	@Override
	public UserApiDto partialUpdateUser(PartialUpdateUserRequestApiDto partialUpdateUserRequestApiDto) {
		logger.info("partially updating user: " + partialUpdateUserRequestApiDto);
		User userDto = mapper.toDto(partialUpdateUserRequestApiDto);
		UserApiDto resposne = mapper.toApiDto(userService.partialUpdateUser(userDto));
		return setServiceAddress(resposne);
	}
	
	@Override
	public DeletedUsersResponseApiDto deleteUser(UUID userUuid) {
		int deletedUsersCount = userService.deleteUser(userUuid);

		return new DeletedUsersResponseApiDto().deletedUsersCount(deletedUsersCount)
				.message("users deleted successfully")
				.serviceAddress(serviceUtil.getServiceAddress());
	}
	
	@Override
	public List<UserApiDto> getSuggestedUsers(String size) {
		logger.info("retrieving suggested users");
		List<User> suggestedUsers =  userService.getSuggestedUsers(size);
		return mapper.toApiDtoList(suggestedUsers);
	}
	
	private UserApiDto setServiceAddress(UserApiDto userApiDto) {

		userApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return userApiDto;
	}




}
