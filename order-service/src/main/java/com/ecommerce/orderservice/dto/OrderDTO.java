package com.ecommerce.orderservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class OrderDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemDTO> items;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    private String paymentMethod;

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class OrderItemDTO {
        @NotNull private Long productId;
        private String productName;
        @NotNull @Positive private Integer quantity;
        @NotNull @Positive private BigDecimal unitPrice;
    }
}
