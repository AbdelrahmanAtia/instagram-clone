package com.javaworld.instagram.authorizationserver.appconfig.security;

import com.javaworld.instagram.authorizationserver.appconfig.jose.Jwks;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;

@Configuration(proxyBeanMethods = false)
@Import(OAuth2AuthorizationServerConfiguration.class)
public class AuthorizationServerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationServerConfig.class);

    @Value("${issuer.uri}")
    private String issuerUri;

    private final Outh2RegisteredClientRepository outh2RegisteredClientRepository;

    public AuthorizationServerConfig(Outh2RegisteredClientRepository outh2RegisteredClientRepository) {
        this.outh2RegisteredClientRepository = outh2RegisteredClientRepository;
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        LOG.info("Register OAUth client allowing all grant flows");
        return new JpaRegisteredClientRepository(outh2RegisteredClientRepository);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        RSAKey rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    @Bean
    public ProviderSettings providerSettings() {
        return new ProviderSettings().issuer(issuerUri);
    }
}
