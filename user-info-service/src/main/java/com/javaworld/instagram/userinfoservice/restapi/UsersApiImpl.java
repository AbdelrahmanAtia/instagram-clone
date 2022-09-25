package com.javaworld.instagram.userinfoservice.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

import com.javaworld.instagram.userinfoservice.commons.utils.ServiceUtil;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.service.UserService;

import reactor.core.publisher.Mono;

@RestController
public class UsersApiImpl implements UsersApi {

	private static final Logger logger = LoggerFactory.getLogger(UsersApiImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserApiDtoMapper mapper;
	
	@Autowired
	private ServiceUtil serviceUtil;

	// TODO: what is the user of ServerWebExchange
	@Override
	public Mono<UserApiDto> createUser(UserApiDto body, ServerWebExchange exchange) {

		UserEntity entity = mapper.apiToEntity(body);

		return userService.createUser(entity)
					.map(e -> mapper.entityToApi(e))
			        .map(e -> setServiceAddress(e));

	}
	
	private UserApiDto setServiceAddress(UserApiDto userApiDto) {
		userApiDto.setServiceAddress(serviceUtil.getServiceAddress());
		return userApiDto;
	}

}
