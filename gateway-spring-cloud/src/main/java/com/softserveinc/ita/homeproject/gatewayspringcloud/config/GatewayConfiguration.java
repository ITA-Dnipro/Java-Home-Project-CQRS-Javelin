package com.softserveinc.ita.homeproject.gatewayspringcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;


@Configuration
public class GatewayConfiguration {

    /**
     * For services access use the same names as defined in spring.application.name property.
     *
     * @param builder
     * @return routes with which Gateway would interact with other services
     */
    @Bean
    public RouteLocator getRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("reader_service", r -> r.method(HttpMethod.GET)
                .uri("lb://READER-SERVICE"))
            .route("writer_service", r -> r.method(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE)
                .uri("lb://WRITER-SERVICE"))
            .build();
    }
}
