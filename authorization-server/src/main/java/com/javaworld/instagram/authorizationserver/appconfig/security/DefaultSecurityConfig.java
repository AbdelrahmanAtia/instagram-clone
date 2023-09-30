//CHECKSTYLE:OFF
package com.javaworld.instagram.authorizationserver.appconfig.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

/**
 * @author Joe Grandja
 * @since 0.1.0
 */
@EnableWebSecurity
public class DefaultSecurityConfig {

  private static final Logger LOG = LoggerFactory.getLogger(DefaultSecurityConfig.class);

  // formatter:off
  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeRequests(authorizeRequests -> authorizeRequests
        .antMatchers("/actuator/**").permitAll()
        
        //TODO: is this really effective ? try removing the following line and see 
        //if the front-end will be affected..if not then this line shall be removed
        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()  // Permit CORS preflight requests
                                                                      
        .anyRequest().authenticated()
      )
      .formLogin(withDefaults());
    return http.build();
  }
  // formatter:on

  // @formatter:off
  @Bean
  UserDetailsService users() {
    UserDetails user = User.withDefaultPasswordEncoder()
      .username("u")
      .password("p")
      .roles("USER")
      .build();
    return new InMemoryUserDetailsManager(user);
  }
  // @formatter:on

}
//CHECKSTYLE:ON