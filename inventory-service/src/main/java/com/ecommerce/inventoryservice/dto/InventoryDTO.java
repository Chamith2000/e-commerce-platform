package com.ecommerce.inventoryservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class InventoryDTO {

    @NotNull(message = "Product ID is required")
    private Long productId;

    private String productName;

    @NotNull(message = "Quantity available is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantityAvailable;

    @Min(value = 0, message = "Reserved quantity cannot be negative")
    private Integer quantityReserved = 0;

    @Min(value = 0, message = "Reorder threshold cannot be negative")
    private Integer reorderThreshold = 10;

    private String warehouseLocation;
}
