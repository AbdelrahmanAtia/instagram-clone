package com.javaworld.instagram.postservice.features.post.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<PostEntity, Long> {

}
