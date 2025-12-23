package com.aureumpicks.ecommerce.config;  // confg → config

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;  // Confguration → Configuration
import org.springframework.web.cors.CorsConfiguration;  // CorsConfguration → CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource;  // CorsConfgurationSource → CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;  // UrlBasedCorsConfgurationSource → UrlBasedCorsConfigurationSource
import java.util.Arrays;

@Configuration  // Confguration → Configuration
public class CorsConfig {  // CorsConfg → CorsConfig
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {  // CorsConfgurationSource, corsConfgurationSource → CorsConfigurationSource, corsConfigurationSource
        CorsConfiguration configuration = new CorsConfiguration();  // CorsConfguration, confguration → CorsConfiguration, configuration

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));  // confguration → configuration
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // confguration → configuration
        configuration.setAllowedHeaders(Arrays.asList("*"));  // confguration → configuration
        configuration.setAllowCredentials(true);  // confguration → configuration
        configuration.setExposedHeaders(Arrays.asList("Authorization"));  // confguration → configuration

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  // UrlBasedCorsConfgurationSource → UrlBasedCorsConfigurationSource
        source.registerCorsConfiguration("/**", configuration);  // registerCorsConfguration, confguration → registerCorsConfiguration, configuration

        return source;
    }
}