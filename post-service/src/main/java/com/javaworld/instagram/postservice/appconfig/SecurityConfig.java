package com.javaworld.instagram.postservice.appconfig;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {	

    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		  .cors().disable()
		  .csrf().disable()
		  .authorizeRequests()
			.antMatchers("/openapi/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/actuator/**").permitAll()
			
			 // TODO: do we need to append the context
			.antMatchers(POST, "/posts/**").hasAuthority("SCOPE_post:write")
			.antMatchers(DELETE, "/posts/**").hasAuthority("SCOPE_post:write")
			.antMatchers(GET, "/posts/**").hasAuthority("SCOPE_post:read")

			.anyRequest().authenticated()
			.and()
		.oauth2ResourceServer()
			.jwt();
    }    
	
}