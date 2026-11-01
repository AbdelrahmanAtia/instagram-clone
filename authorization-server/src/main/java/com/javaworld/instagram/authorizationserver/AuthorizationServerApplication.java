package com.javaworld.instagram.authorizationserver;

import com.javaworld.instagram.authorizationserver.feign.UserServiceClient;
import com.javaworld.instagram.authorizationserver.registereduser.JpaRegisteredUserDetailsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import com.javaworld.instagram.authorizationserver.appconfig.security.CustomClaims;

@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

	/**
	 * this bean is used to customize the jwt token by
	 */
    /*
	@Bean
	OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(CustomClaims customClaims) {
		return context -> {
			if (context.getTokenType() == OAuth2TokenType.ACCESS_TOKEN) {
				Authentication principal = context.getPrincipal();
				Map<String, Object> myCustomClaims = customClaims.getClaims(principal);
				context.getClaims().claims(c -> c.putAll(myCustomClaims));
			}
		};
	}
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserServiceClient userServiceClient) {
        return new JpaRegisteredUserDetailsService(userServiceClient);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }
}
