package com.ecommerce.inventoryservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Inventory Service API",
        version = "1.0",
        description = "Microservice for tracking stock levels, reservations, and warehouse data"
),
        servers = {
                @Server(url = "http://localhost:9000", description = "API Gateway"),
                @Server(url = "http://localhost:8085", description = "Direct access")
        }
)
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }
}
