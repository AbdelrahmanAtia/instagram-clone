package com.javaworld.instagram.authorizationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenCustomizer;

import com.javaworld.instagram.authorizationserver.appconfig.security.CustomClaims;

@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	/**
	 * this bean is used to customize the jwt token by
	 */
	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(CustomClaims customClaims) {
		return context -> {
			if (context.getTokenType() == OAuth2TokenType.ACCESS_TOKEN) {
				Authentication principal = context.getPrincipal();
				context.getClaims().claims(c -> c.putAll(customClaims.getClaims(principal)));
			}
		};
	}

}
