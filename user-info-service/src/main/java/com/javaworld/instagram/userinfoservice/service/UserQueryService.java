package com.javaworld.instagram.userinfoservice.service;

import java.util.List;

import com.javaworld.instagram.userinfoservice.service.criteria.UserCriteria;
import com.javaworld.instagram.userinfoservice.service.dto.User;

public interface UserQueryService {

	
	List<User> findByCriteria(UserCriteria criteria);
}
