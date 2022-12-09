package com.javaworld.instagram.postservice.features.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import com.javaworld.instagram.postservice.features.persistence.entities.PostEntity;
import com.javaworld.instagram.postservice.features.persistence.repositories.PostRepository;



@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED) // disable transactional behavior of spring boot tests which disables 
											// roll-back after  each test function
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED) // TODO: what is the use of this annotation
class PostRepositoryTest {

	@Autowired
	private PostRepository repository;

	private PostEntity savedEntity;
	private UUID savedEntityUuid;

	@BeforeEach
	void setupDb() {

		repository.deleteAll();

		savedEntityUuid = UUID.randomUUID();
		PostEntity entity = new PostEntity(savedEntityUuid, "t1", 1);
		savedEntity = repository.save(entity);

		assertEqualsPost(entity, savedEntity);
	}

	@Test
	void create() {

		PostEntity newEntity = new PostEntity(UUID.randomUUID(), "t2", 1);
		repository.save(newEntity);

		PostEntity foundEntity = repository.findById(newEntity.getId()).get();
		
		assertEqualsPost(newEntity, foundEntity);
		assertEquals(2, repository.count());

	}

	@Test
	void update() {

		savedEntity.setTitle("t1 updated");
		repository.save(savedEntity);

		PostEntity foundEntity = repository.findById(savedEntity.getId()).get();
		
		assertEquals(1, foundEntity.getVersion());
		assertEquals("t1 updated", foundEntity.getTitle());
	}

	@Test
	void delete() {
		repository.delete(savedEntity);
		assertFalse(repository.existsById(savedEntity.getId()));
	}

	@Test
	void getByUserId() {
		List<PostEntity> entityList = repository.findByUserId(savedEntity.getUserId());
		assertThat(entityList, hasSize(1));
		assertEqualsPost(savedEntity, entityList.get(0));
	}

	@Test
	void duplicateError() {
		assertThrows(DataIntegrityViolationException.class, () -> {
			PostEntity entity = new PostEntity(savedEntityUuid, "ttitle", 123);
			repository.save(entity);
		});

	}

	@Test
	void optimisticLockError() {
		
		// Store the saved entity in two separate entity objects
		PostEntity entity1 = repository.findById(savedEntity.getId()).get();
		PostEntity entity2 = repository.findById(savedEntity.getId()).get();

		// Update the entity using the first entity object
		entity1.setTitle("t1 updated");
		repository.save(entity1);

		// Update the entity using the second entity object.
		// This should fail since the second entity now holds an old version number,
		// i.e. an Optimistic Lock Error
		assertThrows(OptimisticLockingFailureException.class, () -> {
			entity2.setTitle("t2");
			repository.save(entity2);
		});

		// Get the updated entity from the database and verify its new sate
		PostEntity updatedEntity = repository.findById(savedEntity.getId()).get();
		assertEquals(1, updatedEntity.getVersion());
		assertEquals("t1 updated", updatedEntity.getTitle());

	}

	private void assertEqualsPost(PostEntity expectedEntity, PostEntity actualEntity) {
		assertEquals(expectedEntity.getPostUuid(), actualEntity.getPostUuid());
		assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
		assertEquals(expectedEntity.getUserId(), actualEntity.getUserId());
		assertEquals(expectedEntity.getTitle(), actualEntity.getTitle());
	}
}