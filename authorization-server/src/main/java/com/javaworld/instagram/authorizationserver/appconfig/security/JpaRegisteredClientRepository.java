package com.javaworld.instagram.authorizationserver.appconfig.security;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public class JpaRegisteredClientRepository implements RegisteredClientRepository {

	private static final Logger logger = LoggerFactory.getLogger(JpaRegisteredClientRepository.class);
    private static final int TOKEN_EXP_TIME_IN_SECONDS = 10800; //3 hrs

	private final Outh2RegisteredClientRepository outh2RegisteredClientRepository;

	public JpaRegisteredClientRepository(Outh2RegisteredClientRepository outh2RegisteredClientRepository) {
		this.outh2RegisteredClientRepository = outh2RegisteredClientRepository;
	}

    @Override
    public RegisteredClient findById(String id) {
        throw new RuntimeException("Not implemented method");
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {

        Outh2RegisteredClientEntity clientEntity = outh2RegisteredClientRepository.findByClientId(clientId)
                .orElseThrow(() ->
                        new RuntimeException("User with username: " + clientId + " not found")
                );

        // TODO: ALL the statically set data such as:-
        //     1- AuthorizationGrantType
        //     2- ClientAuthenticationMethod
        //     3-  scopes (in a separate table as it is a many to many relationship)
        //  shall be stored in db same as clientId & clientSecret

        // TODO: Validate that when the user-service token expires it will request automatically a new acces token from the auth-server and
        //  it will send it in it's outgoing requests to newsfeed service and post service

        return RegisteredClient
                .withId(clientEntity.getId().toString())
                .clientId(clientEntity.getClientId())
                .clientSecret(clientEntity.getClientSecret())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .scope(OidcScopes.OPENID)
                .scope("post-ms:read")
                .scope("post-ms:write")
                .scope("user-ms:read")
                .scope("user-ms:write")
                .scope("newsfeed-ms.read")
                .scope("newsfeed-ms.write")
                .tokenSettings(ts -> ts.accessTokenTimeToLive(Duration.ofSeconds(TOKEN_EXP_TIME_IN_SECONDS)))
                .build();
    }

    /*
	@Override
	public RegisteredClient findByClientId(String clientId) {

		//TODO: THE following two conditions to be removed..this is just a workaround method that is used till i updated the postman tests
		//to use non static users other than reader & writer
		if (clientId.equals("reader")) {
			return getOldReaderClient();
		}

		if (clientId.equals("writer")) {
			return geOldtWriterClient();
		}
		
		
		ClientEntity client = clientRepository.findByClientId(clientId).orElseThrow(() -> {
			return new RuntimeException("User with username: " + clientId + " not found");
		});

		// @formatter:off
		 RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
			      //.clientId("writer")
			      //.clientSecret("secret")
				  .clientId(client.getClientId())
				  .clientSecret(client.getClientSecret())
			      .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
			      .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			      .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
			      .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
			      .redirectUri("https://my.redirect.uri")
			      .redirectUri("https://localhost:8443/webjars/swagger-ui/oauth2-redirect.html")
			      .scope(OidcScopes.OPENID)
			      
			      //TODO: this roles shall not be static..it shall be in DB
			      .scope("post:read")
			      .scope("post:write")
			      .scope("user:read")
			      .scope("user:write")
			      .clientSettings(clientSettings -> clientSettings.requireUserConsent(true))
			      .tokenSettings(ts -> ts.accessTokenTimeToLive(Duration.ofSeconds(TOKEN_EXP_TIME_IN_SECONDS)))
			      .build();
		 // @formatter:on

		return registeredClient;
	}
	
	//TODO: to be removed..this is just a workaround method that is used till i updated the postman tests
	//to use non static users other than reader & writer
	private RegisteredClient getOldReaderClient() {

		RegisteredClient readerClient = RegisteredClient.withId(UUID.randomUUID().toString()).clientId("reader")
				.clientSecret("secret").clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri("https://my.redirect.uri")
				.redirectUri("https://localhost:8443/webjars/swagger-ui/oauth2-redirect.html").scope(OidcScopes.OPENID)
				.scope("post:read").scope("user:read")
				.clientSettings(clientSettings -> clientSettings.requireUserConsent(true))
				.tokenSettings(ts -> ts.accessTokenTimeToLive(Duration.ofHours(1))).build();

		return readerClient;

	}	

	//TODO: to be removed..this is just a workaround method that is used till i updated the postman tests
	//to use non static users other than reader & writer	
	private RegisteredClient geOldtWriterClient() {
		RegisteredClient writerClient = RegisteredClient.withId(UUID.randomUUID().toString()).clientId("writer")
				.clientSecret("secret").clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri("https://my.redirect.uri")
				.redirectUri("https://localhost:8443/webjars/swagger-ui/oauth2-redirect.html").scope(OidcScopes.OPENID)
				.scope("post:read").scope("post:write").scope("user:read").scope("user:write")
				.clientSettings(clientSettings -> clientSettings.requireUserConsent(true))
				.tokenSettings(ts -> ts.accessTokenTimeToLive(Duration.ofHours(1))).build();

		return writerClient;
	}
     */

}