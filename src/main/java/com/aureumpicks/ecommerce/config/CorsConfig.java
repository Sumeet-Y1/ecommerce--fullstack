package com.aureumpicks.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:3000",
                "https://aureumpicks.pages.dev"
                // Add the deployed Cloudflare Pages origin here when ready.
                // "https://aureumpicks.pages.dev",
                // "https://aureumpicks.com"
        ));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
