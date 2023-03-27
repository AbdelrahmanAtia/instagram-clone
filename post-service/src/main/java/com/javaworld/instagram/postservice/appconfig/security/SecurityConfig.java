package com.javaworld.instagram.postservice.appconfig.security;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/openapi/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/actuator/**").permitAll()
				
				// TODO: do we need to append the context
				.antMatchers(POST, "/services/posts/**").hasAuthority("SCOPE_post:write")
				.antMatchers(DELETE, "/services/posts/**").hasAuthority("SCOPE_post:write")
				.antMatchers(GET, "/services/posts/**").hasAuthority("SCOPE_post:read")

				.anyRequest().authenticated()
				.and()
			.oauth2ResourceServer()
				.jwt();
		
		return http.build();
	}
}