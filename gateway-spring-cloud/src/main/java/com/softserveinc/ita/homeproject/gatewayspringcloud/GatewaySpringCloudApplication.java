package com.softserveinc.ita.homeproject.gatewayspringcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;


@SpringBootApplication
public class GatewaySpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySpringCloudApplication.class, args);
    }
    }
