package com.javaworld.instagram.authorizationserver.appconfig.security;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomClaims {

	Logger logger = LoggerFactory.getLogger(CustomClaims.class);

	private final ClientRepository clientRepository;

	public CustomClaims(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	public Map<String, Object> getClaims(Authentication principal) {
		logger.debug("Seting user details in claim  for the user {}", principal.getName());

		Map<String, Object> claims = new HashMap<>();
		String clientId = principal.getName();

		//TODO: to be removed..this is just a workaround for fixed reader &
		//writer users
		if(clientId.equals("reader") || clientId.equals("writer")) {
			claims.put("user_uuid", UUID.randomUUID());
			return claims;
		}
		
		ClientEntity client = clientRepository.findByClientId(clientId).orElseThrow(() -> {
			return new RuntimeException("User with username: " + clientId + " not found");
		});
		
		claims.put("user_uuid", client.getClientUuid().toString());

		return claims;
	}

}