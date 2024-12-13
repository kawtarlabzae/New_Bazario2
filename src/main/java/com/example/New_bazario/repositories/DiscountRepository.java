package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
}