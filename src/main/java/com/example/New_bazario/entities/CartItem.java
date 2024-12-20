package com.example.New_bazario.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "_cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    @Column(name = "cart_id", nullable = false) // Foreign key to Cart
    private Integer cartId; // Store the cart ID instead of a direct relationship

    @Column(name = "order_id", nullable = true) // Foreign key to Cart
    private Integer orderId; 
    
    // Constructors
    public CartItem() {}

    public CartItem(Integer cartId, Product product, Integer quantity) {
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Integer getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Integer cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
