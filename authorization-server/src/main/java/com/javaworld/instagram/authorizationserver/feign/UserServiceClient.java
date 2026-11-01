package com.javaworld.instagram.authorizationserver.feign;

import com.javaworld.instagram.userinfoservice.server.api.ReportsApi;
import com.javaworld.instagram.userinfoservice.server.api.UsersApi;

public interface UserServiceClient {

    UsersApi usersApi();

    ReportsApi reportsApi();
    
}
