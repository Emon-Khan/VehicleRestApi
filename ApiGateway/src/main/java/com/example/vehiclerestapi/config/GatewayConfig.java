package com.example.vehiclerestapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("VEHICLE-SEARCH-API", r -> r.path("/api/v1/vehicle-search/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://VEHICLE-SEARCH-API"))
                .route("VEHICLE-SEARCH-API", r -> r.path("/api/v1/vehicle-search/manufacturers/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://VEHICLE-SEARCH-API"))
                .route("VEHICLE-SEARCH-API", r -> r.path("/api/v1/vehicle-search/model-trim/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://VEHICLE-SEARCH-API"))
                .route("VEHICLE-SEARCH-API", r -> r.path("/api/v1/vehicle-search/vehicle-details/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://VEHICLE-SEARCH-API"))
                .route("VEHICLE-SEARCH-API", r -> r.path("/api/v1/vehicle-search/vehicle-market-price/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://VEHICLE-SEARCH-API"))
                .route("VEHICLE-SEARCH-API", r -> r.path("/actuator/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://VEHICLE-SEARCH-API"))
                .route("VEHICLE-DETAILS-API", r -> r.path("/api/v1/vehicle-details/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://VEHICLE-DETAILS-API"))
                .build();
    }
}
