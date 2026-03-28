package com.ecommerce.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
@Data @NoArgsConstructor @AllArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long productId;

    private String productName;

    @Column(nullable = false)
    private Integer quantityAvailable = 0;

    @Column(nullable = false)
    private Integer quantityReserved = 0;

    @Column(nullable = false)
    private Integer reorderThreshold = 10;

    private String warehouseLocation;

    @Enumerated(EnumType.STRING)
    private StockStatus stockStatus = StockStatus.IN_STOCK;

    public enum StockStatus { IN_STOCK, LOW_STOCK, OUT_OF_STOCK }

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = LocalDateTime.now(); updatedAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        if (quantityAvailable == 0) stockStatus = StockStatus.OUT_OF_STOCK;
        else if (quantityAvailable <= reorderThreshold) stockStatus = StockStatus.LOW_STOCK;
        else stockStatus = StockStatus.IN_STOCK;
    }
}
