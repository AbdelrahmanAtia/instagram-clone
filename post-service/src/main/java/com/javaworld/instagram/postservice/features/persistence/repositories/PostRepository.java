package com.javaworld.instagram.postservice.features.persistence.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

	//@Transactional(readOnly = true)
	List<PostEntity> findByUserId(int userId);
}
