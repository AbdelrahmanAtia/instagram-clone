package com.javaworld.instagram.authorizationserver.feign;

import com.javaworld.instagram.userinfoservice.server.api.UsersApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "UsersApiClient",
        url = "${spring.urls.user-ms}"
)
interface UsersApiClient extends UsersApi {

}