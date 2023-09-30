package com.javaworld.instagram.gateway.appconfig;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("oauth2-server", r -> r
                        .path("/oauth2/**")
                        .filters(f -> f
                            .addResponseHeader("Access-Control-Allow-Origin", "*")
                            .addResponseHeader("Access-Control-Allow-Methods", "*")
                            .addResponseHeader("Access-Control-Allow-Headers", "*")
                            .addResponseHeader("Access-Control-Expose-Headers", "*"))
                        .uri("lb://auth-server"))
                .route("oauth2-login", r -> r
                        .path("/login/**")
                        .filters(f -> f
                            .addResponseHeader("Access-Control-Allow-Origin", "*")
                            .addResponseHeader("Access-Control-Allow-Methods", "*")
                            .addResponseHeader("Access-Control-Allow-Headers", "*")
                            .addResponseHeader("Access-Control-Expose-Headers", "*"))
                        .uri("lb://auth-server"))
                .route("oauth2-error", r -> r
                        .path("/error/**")
                        .filters(f -> f
                            .addResponseHeader("Access-Control-Allow-Origin", "*")
                            .addResponseHeader("Access-Control-Allow-Methods", "*")
                            .addResponseHeader("Access-Control-Allow-Headers", "*")
                            .addResponseHeader("Access-Control-Expose-Headers", "*"))
                        .uri("lb://auth-server"))
                .route("post-micro-service", r -> r
                        .path("/services/post-ms/**")
                        .filters(f -> f
                            .addResponseHeader("Access-Control-Allow-Origin", "*")
                            .addResponseHeader("Access-Control-Allow-Methods", "*")
                            .addResponseHeader("Access-Control-Allow-Headers", "*")
                            .addResponseHeader("Access-Control-Expose-Headers", "*")
                            .rewritePath("/services/post-ms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://post-ms"))
                .route("user-info-micro-service", r -> r
                        .path("/services/user-ms/**")
                        .filters(f -> f
                            .addResponseHeader("Access-Control-Allow-Origin", "*")
                            .addResponseHeader("Access-Control-Allow-Methods", "*")
                            .addResponseHeader("Access-Control-Allow-Headers", "*")
                            .addResponseHeader("Access-Control-Expose-Headers", "*")
                            .rewritePath("/services/user-ms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://user-ms"))
                .build();
        
        
        
    }
}
