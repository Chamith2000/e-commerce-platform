package com.ecommerce.gateway;

import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Configures a unified Swagger UI at http://localhost:9000/swagger-ui.html
 * that aggregates OpenAPI docs from all 6 microservices.
 *
 * Each service appears as a named tab in the Swagger UI "Explore" dropdown.
 * Requests made via "Try it out" are routed through the API Gateway.
 */
@Configuration
public class SwaggerConfig {

    @Primary
    @Bean
    public SwaggerUiConfigProperties swaggerUiConfigProperties() {
        SwaggerUiConfigProperties config = new SwaggerUiConfigProperties();

        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();

        urls.add(swaggerUrl("1. Product Service",      "/product-service/v3/api-docs"));
        urls.add(swaggerUrl("2. Order Service",        "/order-service/v3/api-docs"));
        urls.add(swaggerUrl("3. Payment Service",      "/payment-service/v3/api-docs"));
        urls.add(swaggerUrl("4. Cart Service",         "/cart-service/v3/api-docs"));
        urls.add(swaggerUrl("5. Inventory Service",    "/inventory-service/v3/api-docs"));
        urls.add(swaggerUrl("6. Notification Service", "/notification-service/v3/api-docs"));

        config.setUrls(urls);
        config.setUrlsPrimaryName("1. Product Service");
        config.setOperationsSorter("method");
        config.setTagsSorter("alpha");
        config.setDisplayRequestDuration(true);
        config.setPath("/swagger-ui.html");

        return config;
    }

    private AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl(String name, String url) {
        AbstractSwaggerUiConfigProperties.SwaggerUrl swaggerUrl =
                new AbstractSwaggerUiConfigProperties.SwaggerUrl();
        swaggerUrl.setName(name);
        swaggerUrl.setUrl(url);
        return swaggerUrl;
    }
}