package com.javaworld.instagram.authorizationserver.appconfig.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, String> {

	Optional<ClientEntity> findByClientId(String clientId);
	

}
