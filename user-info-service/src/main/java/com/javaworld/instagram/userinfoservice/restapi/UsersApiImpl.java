package com.javaworld.instagram.userinfoservice.restapi;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.javaworld.instagram.userinfoservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.userinfoservice.server.api.UsersApi;
import com.javaworld.instagram.userinfoservice.server.dto.CreateUserRequestApiDto;
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
		UserApiDto response = mapper.mapToUserApiDto(savedUser);
		return setServiceAddress(response);
	}

	@Override
	public UserApiDto findUser(UUID userUuid, Integer delay, Integer faultPercent) {
		UserApiDto userApiDto = mapper.mapToUserApiDto(userService.findUser(userUuid, delay, faultPercent));
		return setServiceAddress(userApiDto);
	}

	private UserApiDto setServiceAddress(UserApiDto userApiDto) {
		userApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return userApiDto;
	}

}
