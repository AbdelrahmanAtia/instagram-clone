package com.javaworld.instagram.postservice.features.post.persistence;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import reactor.test.StepVerifier;

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED) //disable transactional behavior of spring boot tests which disables roll back after 
                                            //each test function
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // TODO: what is the use of this annotation
class PostRepositoryTest  {

	@Autowired
	private PostRepository repository;

	private PostEntity savedEntity;

	//TODO: don't use the database identity id as a business key - find another suitable
	// business key
	
	@BeforeEach
	void setupDb() {
		StepVerifier.create(repository.deleteAll()).verifyComplete();

		PostEntity entity = new PostEntity(1L, "post number 1");
		
		StepVerifier.create(repository.save(entity))
					.expectNextMatches(createdEntity -> {
							savedEntity = createdEntity;
							return arePostEqual(entity, savedEntity);
						})
					.verifyComplete();
	}

	@Test
	void create() {
		PostEntity newEntity = new PostEntity(2L, "post number 2");

		StepVerifier.create(repository.save(newEntity))
					.expectNextMatches(createdEntity -> newEntity.getId() == createdEntity.getId())
					.verifyComplete();

		StepVerifier.create(repository.findById(newEntity.getId()))
				    .expectNextMatches(foundEntity -> arePostEqual(newEntity, foundEntity))
				    .verifyComplete();

		StepVerifier.create(repository.count())
		            .expectNext(2L)
		            .verifyComplete();
	}

	@Test
	void update() {
		
		savedEntity.setTitle("post number 1 - updated");

		StepVerifier.create(repository.save(savedEntity))
					.expectNextMatches(updatedEntity -> updatedEntity.getTitle().equals("post number 1 - updated"))
					.verifyComplete();

		StepVerifier.create(repository.findById(savedEntity.getId()))
					.expectNextMatches(foundEntity -> foundEntity.getVersion() == 1
							&& foundEntity.getTitle().equals("post number 1 - updated"))
					.verifyComplete();
	}

	@Test
	void delete() {
		
		StepVerifier.create(repository.delete(savedEntity))
		            .verifyComplete();
		
		StepVerifier.create(repository.existsById(savedEntity.getId()))
		            .expectNext(false)
		            .verifyComplete();
	}

	@Test
	void getByProductId() {

		StepVerifier.create(repository.findById(savedEntity.getId()))
				    .expectNextMatches(foundEntity -> arePostEqual(savedEntity, foundEntity))
				    .verifyComplete();
	}

	@Test
	void duplicateError() {
		
		PostEntity entity = new PostEntity(1L, "title");
		
		StepVerifier.create(repository.save(entity))
		            .expectError(DuplicateKeyException.class)
		            .verify();
	}

	@Test
	void optimisticLockError() {

		// Store the saved entity in two separate entity objects
		PostEntity entity1 = repository.findById(savedEntity.getId()).block();
		PostEntity entity2 = repository.findById(savedEntity.getId()).block();

		// Update the entity using the first entity object
		entity1.setTitle("t1");
		repository.save(entity1).block();

		// Update the entity using the second entity object.
		// This should fail since the second entity now holds a old version number, i.e.
		// a Optimistic Lock Error
		StepVerifier.create(repository.save(entity2))
				    .expectError(OptimisticLockingFailureException.class)
				    .verify();

		// Get the updated entity from the database and verify its new sate
		StepVerifier.create(repository.findById(savedEntity.getId()))
				    .expectNextMatches(foundEntity -> foundEntity.getVersion() == 1 && foundEntity.getTitle().equals("t1"))
				    .verifyComplete();
	}
	
	private boolean arePostEqual(PostEntity expectedEntity, PostEntity actualEntity) {
		return (expectedEntity.getId().equals(actualEntity.getId()))
				&& (expectedEntity.getVersion() == actualEntity.getVersion())
				&& (expectedEntity.getTitle() == actualEntity.getTitle());
	}
	
}