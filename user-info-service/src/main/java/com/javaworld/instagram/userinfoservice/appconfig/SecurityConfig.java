package com.javaworld.instagram.userinfoservice.appconfig;

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
		   //TODO: why without disabling cors & csrf u can't access the actuator end points without token
		   //even if u made /actuator/** publicly accessible ?
		  .cors().disable().csrf().disable()
		  .authorizeRequests()
			.antMatchers("/openapi/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/actuator/**").permitAll()
			
			 // TODO: do we need to append the context
			.antMatchers(POST, "/users/**").hasAuthority("SCOPE_post:write")
			.antMatchers(DELETE, "/users/**").hasAuthority("SCOPE_post:write")
			.antMatchers(GET, "/users/**").hasAuthority("SCOPE_post:read")
			.antMatchers(POST, "/db/recreate").hasAuthority("SCOPE_post:write")


			.anyRequest().authenticated()
			.and()
		.oauth2ResourceServer()
			.jwt();
    }    
	
}