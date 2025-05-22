package com.iron.api_kite_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {

    //todo: indicar las rutas correctas para que se gestionen bien las peticiones
    @Bean
    public RouteLocator customeRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("grades", r -> r.path("/api/grades")
                        .uri("lb://grades-data-service"))
                .route("student", r -> r.path("/api/student/**")
                        .uri("lb://student-info-service"))
                .route("courses", r -> r.path("/api/course/**")
                        .uri("lb://grades-data-service")
                ).build();
    }
}
