package com.ecommerce.cartservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Cart Service API",
        version = "1.0",
        description = "Microservice for managing shopping cart operations"
),
        servers = {
                @Server(url = "http://localhost:9000", description = "API Gateway"),
                @Server(url = "http://localhost:8084", description = "Direct access")
        }
)
public class CartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }
}
