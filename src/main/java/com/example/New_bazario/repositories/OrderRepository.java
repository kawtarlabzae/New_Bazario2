package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser_Id(Integer userId); // Retrieve orders by user ID
}
