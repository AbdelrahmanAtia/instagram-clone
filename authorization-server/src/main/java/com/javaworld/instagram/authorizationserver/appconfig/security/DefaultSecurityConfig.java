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
        .anyRequest().authenticated()
      )
      .formLogin(withDefaults());
      //.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    return http.build();
  }
  // formatter:on

  // @formatter:off
  
  //TODO: what is the use of the following bean ??? when it is used?? is it for protecting eureka ?
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