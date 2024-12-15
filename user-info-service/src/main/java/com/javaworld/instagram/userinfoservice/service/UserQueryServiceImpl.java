package com.javaworld.instagram.userinfoservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.userinfoservice.service.criteria.UserCriteria;
import com.javaworld.instagram.userinfoservice.service.dto.User;
import com.javaworld.instagram.userinfoservice.service.dtomapper.UserMapper;

import tech.jhipster.service.QueryService;

import com.javaworld.instagram.commonlib.util.StringUitl;
import com.javaworld.instagram.userinfoservice.persistence.UserEntity;
import com.javaworld.instagram.userinfoservice.persistence.UserRepository;

import org.springframework.data.jpa.domain.Specification;

import com.javaworld.instagram.userinfoservice.persistence.UserEntity_;

@Service
public class UserQueryServiceImpl extends QueryService<UserEntity> implements UserQueryService {

	private static final Logger logger = LoggerFactory.getLogger(UserQueryServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional(readOnly = true)
	public List<User> findByCriteria(UserCriteria criteria) {
		logger.info("find users by criteria : {}", criteria);
		final Specification<UserEntity> specification = createSearchSpecification(criteria);
		return userMapper.toDto(userRepository.findAll(specification));
	}

	protected Specification<UserEntity> createSearchSpecification(UserCriteria criteria) {
		Specification<UserEntity> specification = Specification.where(null);
		if (criteria != null) {
			// This has to be called first, because the distinct method returns null
			if (criteria.getName() != null && !StringUitl.isBlankOrNull(criteria.getName().getContains())) {
				specification = specification.and(buildStringSpecification(criteria.getName(), UserEntity_.username));
			}

			if (criteria.getFullName() != null && !StringUitl.isBlankOrNull(criteria.getFullName().getContains())) {
				specification = specification.or(buildStringSpecification(criteria.getName(), UserEntity_.fullName));
			}

		}
		return specification;
	}

}
