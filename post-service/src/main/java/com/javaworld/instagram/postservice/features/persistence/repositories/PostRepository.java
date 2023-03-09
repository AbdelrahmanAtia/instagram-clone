package com.javaworld.instagram.postservice.features.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

	//TODO: USE @Transactional(readOnly = true)
	List<PostEntity> findByUserUuidOrderByCreatedAtDesc(UUID userUuid);
	
	//TODO: USE @Transactional(readOnly = true)
	List<PostEntity> findByUserUuid(UUID userUuid);
	
	void deleteByPostUuidIn(List<UUID> postUuid);

}
