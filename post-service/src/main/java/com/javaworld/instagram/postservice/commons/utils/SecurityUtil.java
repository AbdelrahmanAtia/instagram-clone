package com.javaworld.instagram.postservice.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.UUID;

public class SecurityUtil {

	private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

	public static UUID getUserUuidFromAccessToken(SecurityContext sc) {
		if (sc != null && sc.getAuthentication() != null && sc.getAuthentication() instanceof JwtAuthenticationToken) {
			Jwt jwtToken = ((JwtAuthenticationToken) sc.getAuthentication()).getToken();
			Object userUuidObject = jwtToken.getClaims().get("user_uuid");
			return UUID.fromString((String) userUuidObject);
		} else {
			throw new RuntimeException("No JWT based Authentication supplied, running tests are we?");
		}
	}
}