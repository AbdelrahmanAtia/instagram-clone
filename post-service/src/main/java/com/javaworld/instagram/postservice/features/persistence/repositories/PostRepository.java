package com.javaworld.instagram.postservice.features.persistence.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {

	Page<PostEntity> findByUserUuid(UUID userUuid, Pageable pageable);
	
	List<PostEntity> findByUserUuid(UUID userUuid);
	
	int deleteByPostUuidIn(List<UUID> postUuid);
	
	int countByUserUuid(UUID userUuid);
	
	int deleteByUserUuid(UUID userUuid);



}
