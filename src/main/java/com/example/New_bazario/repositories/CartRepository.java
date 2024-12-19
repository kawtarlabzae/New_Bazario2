package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {

}
