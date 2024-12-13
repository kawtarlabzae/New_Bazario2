package com.example.New_bazario.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "_inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer stockQuantity;

    // Constructors
    public Inventory() {}

    public Inventory(Product product, Integer stockQuantity) {
        this.product = product;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}