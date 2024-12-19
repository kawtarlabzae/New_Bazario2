package com.example.New_bazario.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "_order")
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer orderId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<CartItem> orderItems;

    private LocalDateTime createdAt;

    private boolean paid;

    // Constructors, Getters, and Setters
    public Order() {}

    public Order( LocalDateTime createdAt, boolean paid) {
 
        this.createdAt = createdAt;
        this.paid = paid;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

   

    public Set<CartItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<CartItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }


}
