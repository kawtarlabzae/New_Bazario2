package com.example.New_bazario.services;

import com.example.New_bazario.entities.Address;

import com.example.New_bazario.repositories.AddressRepository;
import com.example.New_bazario.security.user.User;
import com.example.New_bazario.security.user.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Address addAddress(Integer userId, Address address) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        address.setUser(user);
        return addressRepository.save(address);
    }

    public List<Address> getAddressesByUserId(Integer userId) {
        return addressRepository.findByUser_Id(userId);
    }
}
