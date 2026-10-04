package com.javaworld.instagram.authorizationserver.appconfig.security;

import java.util.Optional;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Outh2RegisteredClientRepository extends JpaRepository<Outh2RegisteredClientEntity, UUID> {

	Optional<Outh2RegisteredClientEntity> findByClientId(String clientId);
}
