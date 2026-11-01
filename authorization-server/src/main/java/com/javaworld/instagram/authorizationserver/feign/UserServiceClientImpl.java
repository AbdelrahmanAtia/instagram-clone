package com.javaworld.instagram.authorizationserver.feign;

import com.javaworld.instagram.userinfoservice.server.api.ReportsApi;
import com.javaworld.instagram.userinfoservice.server.api.UsersApi;
import org.springframework.stereotype.Service;

@Service
public class UserServiceClientImpl implements UserServiceClient {

    private final UsersApiClient usersApiClient;
    private final ReportsApiClient reportsApiClient;

    public UserServiceClientImpl(
            UsersApiClient usersApiClient,
            ReportsApiClient reportsApiClient) {

        this.usersApiClient = usersApiClient;
        this.reportsApiClient = reportsApiClient;
    }

    @Override
    public UsersApi usersApi() {
        return usersApiClient;
    }

    @Override
    public ReportsApi reportsApi() {
        return reportsApiClient;
    }
}
