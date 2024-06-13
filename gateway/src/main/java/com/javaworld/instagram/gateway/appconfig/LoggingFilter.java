package com.javaworld.instagram.gateway.appconfig;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Order(-1)
public class LoggingFilter implements GlobalFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        logRequest(request);

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> logResponse(response))
                .doOnError(throwable -> logError(throwable, exchange));
    }

    private void logRequest(ServerHttpRequest request) {
    	
    	HttpHeaders headers = request.getHeaders();
        logger.info("##################### START REQUEST #####################");
        logger.info(">> Method: {}", request.getMethod());
        logger.info(">> URL: {}", request.getURI());
        
        logger.info(">> Headers:");
        headers.forEach((key, value) -> logger.info("     {}: {}", key, value));

        logger.info("##################### END REQUEST #####################");
    }

    private void logResponse(ServerHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        logger.info("##################### START RESPONSE #####################");
        logger.info(">> Status Code: {}", response.getStatusCode());

        logger.info(">> Headers:");
        headers.forEach((key, value) -> logger.info("     {}: {}", key, value));

        logger.info("##################### END RESPONSE #####################");
    }

    private void logError(Throwable throwable, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        logger.error("##################### START ERROR #####################");
        logger.error("Error occurred while processing request {} {}: {}", request.getMethod(), request.getURI(), throwable.getMessage());
        logger.error("##################### END ERROR #####################");
        throwable.printStackTrace();
    }
    
}
