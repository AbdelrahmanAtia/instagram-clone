package com.javaworld.instagram.authorizationserver.registereduser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredUserRepository
        extends JpaRepository<RegisteredUserEntity, Long> {

    Optional<RegisteredUserEntity> findByUsername(String username);
}