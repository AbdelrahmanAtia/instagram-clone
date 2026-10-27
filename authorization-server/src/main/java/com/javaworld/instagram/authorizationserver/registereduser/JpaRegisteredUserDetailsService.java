package com.javaworld.instagram.authorizationserver.registereduser;


import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JpaRegisteredUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JpaRegisteredUserDetailsService.class);

    private final RegisteredUserRepository registeredUserRepository;

    public JpaRegisteredUserDetailsService(
            RegisteredUserRepository registeredUserRepository) {
        this.registeredUserRepository = registeredUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        logger.info(
                "starting JpaRegisteredUserRepository.loadUserByUsername(username = {})",
                username);

        RegisteredUserEntity userEntity = registeredUserRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with username: " + username + " not found"));

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}