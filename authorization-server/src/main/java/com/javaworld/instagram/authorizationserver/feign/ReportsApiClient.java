package com.javaworld.instagram.authorizationserver.feign;

import com.javaworld.instagram.userinfoservice.server.api.ReportsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "ReportsApiClient",
        url = "${spring.urls.user-ms}"
)
interface ReportsApiClient extends ReportsApi {

}
