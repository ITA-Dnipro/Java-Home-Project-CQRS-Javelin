package com.softserveinc.ita.homeproject.gatewayspringcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class GatewaySpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySpringCloudApplication.class, args);
    }
    }
