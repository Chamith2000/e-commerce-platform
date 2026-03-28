package com.ecommerce.orderservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Order Service API",
        version = "1.0",
        description = "Microservice responsible for managing customer orders"
),
        servers = {
                @Server(url = "http://localhost:9000", description = "API Gateway"),
                @Server(url = "http://localhost:8082", description = "Direct access")
        }
)
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
