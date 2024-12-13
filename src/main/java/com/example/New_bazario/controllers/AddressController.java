package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Address;
import com.example.New_bazario.services.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{userId}")
    public Address addAddress(@PathVariable Integer userId, @RequestBody Address address) {
        return addressService.addAddress(userId, address);
    }

    @GetMapping("/user/{userId}")
    public List<Address> getAddressesByUserId(@PathVariable Integer userId) {
        return addressService.getAddressesByUserId(userId);
    }
}
