package com.example.New_bazario.repositories;

import com.example.New_bazario.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUser_Id(Integer userId); // Retrieve addresses by user ID
}
