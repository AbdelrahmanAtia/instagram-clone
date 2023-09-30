package com.javaworld.instagram.gateway.appconfig;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class RequestResponseLoggingFilter extends AbstractGatewayFilterFactory<RequestResponseLoggingFilter.Config> {

    private final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final int MAX_BODY_SIZE = 4096;  // Limit body logging to 4KB

    public RequestResponseLoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logRequest(exchange);

            // Buffer the request body for logging and re-read further in the chain
            return DataBufferUtils.join(exchange.getRequest().getBody(), MAX_BODY_SIZE)
            	    .doOnNext(dataBuffer -> {
            	        if (dataBuffer.readableByteCount() > 0) {
            	            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            	            dataBuffer.read(bytes);
            	            logger.info("Request Body: {}", new String(bytes, StandardCharsets.UTF_8));
            	        }
            	    })
            	    .flatMap(dataBuffer -> {
            	        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().build();
            	        exchange.getAttributes().put(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR, dataBuffer);
            	        return chain.filter(exchange.mutate().request(mutatedRequest).build());
            	    })
            	    .then(Mono.fromRunnable(() -> logResponse(exchange)));

        };
    }

    private void logRequest(ServerWebExchange exchange) {
        logger.info("----------------------------- Request -----------------------------");
        ServerHttpRequest request = exchange.getRequest();

        logger.info("Method: {}", request.getMethod());
        logger.info("URL: {}", request.getURI());

        // Logging Headers
        MultiValueMap<String, String> headers = request.getHeaders();
        headers.forEach((key, value) -> logger.info("Header: {}={}", key, value));
    }

    private void logResponse(ServerWebExchange exchange) {
        logger.info("----------------------------- Response -----------------------------");
        logger.info("Status Code: {}", exchange.getResponse().getStatusCode());
        logger.info("-------------------------------------------------------------------");
    }

    public static class Config {
        // You can add configuration options here if needed
    }
}
