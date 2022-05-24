package com.softserveinc.ita.homeproject.gatewayspringcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


@Configuration
public class GatewayConfiguration {

    /**
     * Used environment variable for reader service access.
     * Example:
     * http://localhost:8085 - for running from IDEA
     * http://host.docker.internal:8085 - for running from Docker
     * @param builder
     * @return routes with which Gateway would interact with other services
     */
    @Bean
    public RouteLocator getRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.method(HttpMethod.GET)
                .uri("http://host.docker.internal:8085/api/0/**"))
            .route(r -> r.method(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE)
                .uri("http://host.docker.internal:8086/api/0/**"))
            .build();
    }
}
