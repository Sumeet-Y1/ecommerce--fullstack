package com.aureumpicks.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    // Remove the @GetMapping("/") - let Spring serve static index.html instead

    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Application is healthy");
        return response;
    }

    @GetMapping("/api")
    public Map<String, Object> apiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Aureum Picks E-commerce API is running!");
        response.put("version", "1.0.0");
        response.put("endpoints", Map.of(
                "health", "/health",
                "auth", "/api/auth",
                "products", "/api/products"
        ));
        return response;
    }
}