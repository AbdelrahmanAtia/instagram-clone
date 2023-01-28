package com.javaworld.instagram.postservice.features.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

	//TODO: USE @Transactional(readOnly = true)
	List<PostEntity> findByUserIdOrderByCreatedAtDesc(int userId);
	
	
	//TODO: USE @Transactional(readOnly = true)
	List<PostEntity> findByUserId(int userId);
	
	void deleteByPostUuidIn(List<UUID> postUuid);

}
