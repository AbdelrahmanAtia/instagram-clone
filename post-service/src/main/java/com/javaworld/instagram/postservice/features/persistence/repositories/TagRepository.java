package com.javaworld.instagram.postservice.features.persistence.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.javaworld.instagram.postservice.features.persistence.entities.TagEntity;

public interface TagRepository extends CrudRepository<TagEntity, Integer> {

	Optional<TagEntity> findByName(String name);

}