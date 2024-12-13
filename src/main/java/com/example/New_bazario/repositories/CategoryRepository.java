package com.example.New_bazario.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.New_bazario.entities.*;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Optional<Category> findByName(String name);
}
