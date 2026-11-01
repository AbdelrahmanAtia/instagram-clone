package com.javaworld.instagram.authorizationserver.registereduser;

import com.javaworld.instagram.authorizationserver.feign.UserServiceClient;
import com.javaworld.instagram.userinfoservice.server.dto.UserApiDto;
import feign.FeignException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JpaRegisteredUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JpaRegisteredUserDetailsService.class);

    private final UserServiceClient userServiceClient;

    public JpaRegisteredUserDetailsService(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        logger.info(
                "starting JpaRegisteredUserDetailsService.loadUserByUsername(username = {})",
                username);

        //TODO: literal string shall be part of generated api dtos in both user-service and the generated client

        ResponseEntity<UserApiDto> userApiDto = null;

        // TODO: find a better way for error propagation in case of exceptions..and handle common scenarios like
        //  invalid credentials or when service is down
        try {
           //TODO: since search user api is an internal api, we need to find a way to prevent calling it from outside our K8s cluster
           userApiDto = userServiceClient.usersApi().searchUser(username, "USER_NAME");
        } catch (FeignException ex) {
            logger.error("An error occurred while calling user-service. Error message: {} ",
                    ex.getMessage());
            throw ex;
        }

        //TODO: what if userApiDto.getBody() is null ??

        return User.builder()
                .username(userApiDto.getBody().getUsername())
                .password(userApiDto.getBody().getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
