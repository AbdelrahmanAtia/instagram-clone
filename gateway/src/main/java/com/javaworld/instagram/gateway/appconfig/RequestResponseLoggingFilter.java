package com.javaworld.instagram.gateway.appconfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Order(-1) // Specify the order to ensure this filter runs first
public class RequestResponseLoggingFilter extends AbstractGatewayFilterFactory<RequestResponseLoggingFilter.Config> {

    private final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    public RequestResponseLoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logger.info("Requesttttt: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                logger.info("Responseeeee status code: {}", exchange.getResponse().getStatusCode());
            }));
        };
    }

    public static class Config {
        // You can add configuration options here if needed
    }
}
