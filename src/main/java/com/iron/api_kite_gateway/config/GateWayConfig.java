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
                .route("kites", r -> r.path("/api/kite/**")
                        .uri("lb://kite-service"))
                .route("persons", r -> r.path("/api/person/**")
                        .uri("lb://person-service")
                ).build();
    }
}
