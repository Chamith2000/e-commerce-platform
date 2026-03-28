package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.dto.InventoryDTO;
import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findByStockStatus(Inventory.StockStatus.LOW_STOCK);
    }

    public List<Inventory> getOutOfStockItems() {
        return inventoryRepository.findByStockStatus(Inventory.StockStatus.OUT_OF_STOCK);
    }

    public Inventory createInventory(InventoryDTO dto) {
        if (inventoryRepository.existsByProductId(dto.getProductId())) {
            throw new RuntimeException("Inventory already exists for product ID: " + dto.getProductId());
        }
        Inventory inventory = new Inventory();
        mapDtoToInventory(dto, inventory);
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Long id, InventoryDTO dto) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory record not found with id: " + id));
        mapDtoToInventory(dto, inventory);
        return inventoryRepository.save(inventory);
    }

    public Inventory restockProduct(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantity);
        return inventoryRepository.save(inventory);
    }

    public Inventory reserveStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
        if (inventory.getQuantityAvailable() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + productId);
        }
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - quantity);
        inventory.setQuantityReserved(inventory.getQuantityReserved() + quantity);
        return inventoryRepository.save(inventory);
    }

    public Inventory releaseStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));
        int release = Math.min(quantity, inventory.getQuantityReserved());
        inventory.setQuantityReserved(inventory.getQuantityReserved() - release);
        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + release);
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new RuntimeException("Inventory record not found with id: " + id);
        }
        inventoryRepository.deleteById(id);
    }

    private void mapDtoToInventory(InventoryDTO dto, Inventory inventory) {
        inventory.setProductId(dto.getProductId());
        inventory.setProductName(dto.getProductName());
        inventory.setQuantityAvailable(dto.getQuantityAvailable());
        inventory.setQuantityReserved(dto.getQuantityReserved() != null ? dto.getQuantityReserved() : 0);
        inventory.setReorderThreshold(dto.getReorderThreshold() != null ? dto.getReorderThreshold() : 10);
        inventory.setWarehouseLocation(dto.getWarehouseLocation());
    }
}
