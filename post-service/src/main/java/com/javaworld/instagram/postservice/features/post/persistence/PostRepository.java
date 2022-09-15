package com.javaworld.instagram.postservice.features.post.persistence;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {

}
