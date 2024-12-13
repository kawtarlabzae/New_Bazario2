package com.example.New_bazario.services;

import com.example.New_bazario.entities.Inventory;
import com.example.New_bazario.entities.Product;
import com.example.New_bazario.repositories.InventoryRepository;
import com.example.New_bazario.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public Inventory addInventory(Integer productId, Integer stockQuantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        Inventory inventory = new Inventory(product, stockQuantity);
        return inventoryRepository.save(inventory);
    }

    public Inventory getInventoryById(Integer inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found with ID: " + inventoryId));
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public void deleteInventory(Integer inventoryId) {
        inventoryRepository.deleteById(inventoryId);
    }
}