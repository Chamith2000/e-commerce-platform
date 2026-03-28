package com.ecommerce.notificationservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Notification Service API",
        version = "1.0",
        description = "Microservice for sending and managing user notifications (Email, SMS, Push)"
),
        servers = {
                @Server(url = "http://localhost:9000", description = "API Gateway"),
                @Server(url = "http://localhost:8086", description = "Direct access")
        }
)
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}
