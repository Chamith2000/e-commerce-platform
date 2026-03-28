package com.ecommerce.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * API Gateway - Single entry point for all E-Commerce microservices.
 * Runs on port 9000 and routes requests to services on ports 8081-8086.
 *
 * Routes:
 *   /api/products/**       → Product Service      (8081)
 *   /api/orders/**         → Order Service        (8082)
 *   /api/payments/**       → Payment Service      (8083)
 *   /api/cart/**           → Cart Service         (8084)
 *   /api/inventory/**      → Inventory Service    (8085)
 *   /api/notifications/**  → Notification Service (8086)
 */
@SpringBootApplication
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
