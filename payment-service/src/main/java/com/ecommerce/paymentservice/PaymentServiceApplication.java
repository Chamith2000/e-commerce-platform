package com.ecommerce.paymentservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Payment Service API",
        version = "1.0",
        description = "Microservice for handling payment transactions and refunds"
),
        servers = {
                @Server(url = "http://localhost:9000", description = "API Gateway"),
                @Server(url = "http://localhost:8083", description = "Direct access")
        }
)
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
