package com.javaworld.instagram.postservice.features.post.persistence;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostRepository extends ReactiveCrudRepository<PostEntity, Long> {

}
