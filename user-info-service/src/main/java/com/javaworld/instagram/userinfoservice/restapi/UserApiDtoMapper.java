package com.javaworld.instagram.userinfoservice.restapi;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.javaworld.instagram.userinfoservice.server.dto.CreateUserRequestApiDto;
import com.javaworld.instagram.userinfoservice.server.dto.UserApiDto;
import com.javaworld.instagram.userinfoservice.service.dto.User;

@Mapper(componentModel = "spring")
public interface UserApiDtoMapper {

	UserApiDto mapToUserApiDto(User user);

	@Mapping(target = "mobileNumber", source = "apiDto", qualifiedByName = "mobileNumberOrEmailToMobileNumber")
	@Mapping(target = "email", source = "apiDto", qualifiedByName = "mobileNumberOrEmailToEmail")
	User mapCreateUserRequestToUserDto(CreateUserRequestApiDto apiDto);

	@Named("mobileNumberOrEmailToMobileNumber")
	default String mobileNumberOrEmailToMobileNumber(CreateUserRequestApiDto apiDto) {
		return isMobileNumber(apiDto.getMobileNumberOrEmail()) ? apiDto.getMobileNumberOrEmail() : null;
	}

	@Named("mobileNumberOrEmailToEmail")
	default String mobileNumberOrEmailToEmail(CreateUserRequestApiDto apiDto) {
		return isEmail(apiDto.getMobileNumberOrEmail()) ? apiDto.getMobileNumberOrEmail() : null;
	}

	default boolean isMobileNumber(String value) {
		// the pattern \\d+ will match any sequence of digits, of any length greater
		// than zero.
		return value != null && value.matches("\\d+");
	}

	default boolean isEmail(String value) {
		return value != null && value.contains("@");
	}
}