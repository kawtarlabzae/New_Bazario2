package com.example.New_bazario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.New_bazario.entities.*;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // JpaRepository already provides basic CRUD operations
}
