package com.javaworld.instagram.authorizationserver.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(
        clients = {
                UsersApiClient.class,
                ReportsApiClient.class
        })
public class FeignConfiguration {

}