/*
 * package com.javaworld.instagram.userinfoservice; import static
 * org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertFalse; import static
 * org.junit.jupiter.api.Assertions.assertThrows; import static
 * org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;
 * 
 * import org.junit.jupiter.api.BeforeEach; import org.junit.jupiter.api.Test;
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
 * import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
 * import org.springframework.dao.DuplicateKeyException; import
 * org.springframework.dao.OptimisticLockingFailureException; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import com.javaworld.instagram.userinfoservice.persistence.UserEntity; import
 * com.javaworld.instagram.userinfoservice.persistence.UserRepository;
 * 
 * @DataJpaTest
 * 
 * @Transactional(propagation = NOT_SUPPORTED) // disable transactional behavior
 * of spring boot tests which disables // roll-back after each test function
 * 
 * @AutoConfigureTestDatabase(replace =
 * AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED) // TODO: what is the use
 * of this annotation class UserRepositoryTest {
 * 
 * @Autowired private UserRepository repository;
 * 
 * private UserEntity savedEntity;
 * 
 * @BeforeEach void setupDb() { repository.deleteAll().block();
 * 
 * UserEntity entity = new UserEntity("magnuslarsson",
 * "magnuslarsson@gmail.com", "magnus", "mypass12"); savedEntity =
 * repository.save(entity).block();
 * 
 * assertEqualsUser(entity, savedEntity); }
 * 
 * @Test void create() {
 * 
 * UserEntity newEntity = new UserEntity("lexfridman", "lexfridman@gmail.com",
 * "lex", "pass227");
 * 
 * repository.save(newEntity).block();
 * 
 * UserEntity foundEntity =
 * repository.findById(newEntity.getUsername()).block();
 * assertEqualsUser(newEntity, foundEntity);
 * 
 * assertEquals(2, (long) repository.count().block()); }
 * 
 * @Test void update() { savedEntity.setName("a2");
 * repository.save(savedEntity).block();
 * 
 * UserEntity foundEntity =
 * repository.findById(savedEntity.getUsername()).block(); assertEquals(1,
 * (long) foundEntity.getVersion()); assertEquals("a2", foundEntity.getName());
 * }
 * 
 * @Test void delete() { repository.delete(savedEntity).block();
 * assertFalse(repository.existsById(savedEntity.getUsername()).block()); }
 * 
 * @Test void duplicateError() { assertThrows(DuplicateKeyException.class, () ->
 * { //adding a user with a username already existing in the data base
 * UserEntity entity = new UserEntity("magnuslarsson", "email2", "name2",
 * "pass2"); repository.save(entity).block(); }); }
 * 
 * @Test void optimisticLockError() {
 * 
 * // Store the saved entity in two separate entity objects UserEntity entity1 =
 * repository.findById(savedEntity.getUsername()).block(); UserEntity entity2 =
 * repository.findById(savedEntity.getUsername()).block();
 * 
 * // Update the entity using the first entity object entity1.setName("a1");
 * repository.save(entity1).block();
 * 
 * // Update the entity using the second entity object. // This should fail
 * since the second entity now holds an old version number, // i.e. an
 * Optimistic Lock Error assertThrows(OptimisticLockingFailureException.class,
 * () -> { entity2.setName("a2"); repository.save(entity2).block(); });
 * 
 * // Get the updated entity from the database and verify its new sate
 * UserEntity updatedEntity =
 * repository.findById(savedEntity.getUsername()).block(); assertEquals(1, (int)
 * updatedEntity.getVersion()); assertEquals("a1", updatedEntity.getName()); }
 * 
 * private void assertEqualsUser(UserEntity expectedEntity, UserEntity
 * actualEntity) { assertEquals(expectedEntity.getUsername(),
 * actualEntity.getUsername()); assertEquals(expectedEntity.getVersion(),
 * actualEntity.getVersion()); assertEquals(expectedEntity.getEmail(),
 * actualEntity.getEmail()); assertEquals(expectedEntity.getName(),
 * actualEntity.getName()); assertEquals(expectedEntity.getPassword(),
 * actualEntity.getPassword()); } }
 */