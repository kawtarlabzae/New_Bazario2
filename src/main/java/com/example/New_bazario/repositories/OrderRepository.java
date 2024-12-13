package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o JOIN FETCH o.cart WHERE o.user.id = :userId")
    List<Order> findOrdersWithCartsByUserId(@Param("userId") Integer userId);
}
