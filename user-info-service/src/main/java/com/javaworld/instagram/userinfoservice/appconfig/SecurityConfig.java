package com.javaworld.instagram.userinfoservice.appconfig;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Profile("!test")
public class SecurityConfig extends WebSecurityConfigurerAdapter  {	

    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		   //TODO: why without disabling cors & csrf u can't access the actuator end points without token
		   //even if u made /actuator/** publicly accessible ?
		  .cors().disable().csrf().disable()
		  .authorizeRequests()
			.antMatchers(
                    "/openapi/**",
                    "/internal/**",
                    "/webjars/**",
                    "/actuator/**")
                .permitAll()
			 // TODO: do we need to append the context
			.antMatchers(POST, "/api/users/").permitAll()  //registering user doesn't need to be protected
			.antMatchers(DELETE, "/api/users/**").hasAuthority("SCOPE_post:write") //TODO: Why this is public ?
			.antMatchers(GET, "/users/**").hasAuthority("SCOPE_post:read") //TODO: Why this is public ?
			.antMatchers(POST, "/db/recreate").hasAuthority("SCOPE_post:write") //TODO: Why this is public ?
			.antMatchers(POST, "/db/clear").hasAuthority("SCOPE_post:write") //TODO: Why this is public ?
			.anyRequest().authenticated()
			.and()
		.oauth2ResourceServer()
			.jwt();
    }    
	
}