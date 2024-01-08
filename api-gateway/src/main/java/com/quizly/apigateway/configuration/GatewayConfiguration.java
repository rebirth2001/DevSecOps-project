package com.quizly.apigateway.configuration;

import com.quizly.apigateway.security.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {
    private final JwtAuthenticationFilter jwtFilter;

    GatewayConfiguration(JwtAuthenticationFilter jwtFilter){
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("users-service",predicateSpec -> predicateSpec.path("/api/users/**")
                        .filters(f -> f.filter(jwtFilter).stripPrefix(2))
                        .uri("lb://users-service"))
                .route("quizs-service",predicateSpec -> predicateSpec.path("/api/quizs/**")
                        .filters(f->f.filter(jwtFilter).stripPrefix(2))
                        .uri("lb://quizs-service"))
                .build();

    }
}
