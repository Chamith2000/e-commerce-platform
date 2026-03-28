package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.dto.InventoryDTO;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Service", description = "Tracks stock levels and manages warehouse inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @Operation(summary = "Get all inventory records")
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get inventory record by ID")
    public ResponseEntity<?> getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    @Operation(summary = "Get inventory by product ID")
    public ResponseEntity<?> getByProductId(@PathVariable Long productId) {
        return inventoryService.getInventoryByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get all low stock items")
    public ResponseEntity<List<Inventory>> getLowStock() {
        return ResponseEntity.ok(inventoryService.getLowStockItems());
    }

    @GetMapping("/out-of-stock")
    @Operation(summary = "Get all out-of-stock items")
    public ResponseEntity<List<Inventory>> getOutOfStock() {
        return ResponseEntity.ok(inventoryService.getOutOfStockItems());
    }

    @PostMapping
    @Operation(summary = "Create a new inventory record")
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update inventory record")
    public ResponseEntity<?> updateInventory(@PathVariable Long id, @Valid @RequestBody InventoryDTO dto) {
        try {
            return ResponseEntity.ok(inventoryService.updateInventory(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/product/{productId}/restock")
    @Operation(summary = "Restock a product", description = "Adds quantity to the available stock")
    public ResponseEntity<?> restock(@PathVariable Long productId, @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(inventoryService.restockProduct(productId, quantity));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/product/{productId}/reserve")
    @Operation(summary = "Reserve stock for an order")
    public ResponseEntity<?> reserve(@PathVariable Long productId, @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(inventoryService.reserveStock(productId, quantity));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/product/{productId}/release")
    @Operation(summary = "Release reserved stock back to available")
    public ResponseEntity<?> release(@PathVariable Long productId, @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(inventoryService.releaseStock(productId, quantity));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an inventory record")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        try {
            inventoryService.deleteInventory(id);
            return ResponseEntity.ok(Map.of("message", "Inventory record deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
