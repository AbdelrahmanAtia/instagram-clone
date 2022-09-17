package com.javaworld.instagram.postservice.features.post.persistence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

	@Transactional(readOnly = true)
	List<PostEntity> findByUserId(int userId);
}
