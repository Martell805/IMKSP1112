package org.example.anylogicspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class AnyLogicSpringBootApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AnyLogicSpringBootApplication.class, args);
    }
    @Bean
    public CorsConfiguration corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");

        return configuration;
    }
}
