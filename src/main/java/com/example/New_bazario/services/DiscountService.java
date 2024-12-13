package com.example.New_bazario.services;

import com.example.New_bazario.entities.Discount;
import com.example.New_bazario.entities.Product;
import com.example.New_bazario.repositories.DiscountRepository;
import com.example.New_bazario.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;

    public DiscountService(DiscountRepository discountRepository, ProductRepository productRepository) {
        this.discountRepository = discountRepository;
        this.productRepository = productRepository;
    }

    public Discount addDiscount(Integer productId, BigDecimal discountPercentage, LocalDateTime startDate, LocalDateTime endDate) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        Discount discount = new Discount(product, discountPercentage, startDate, endDate);
        return discountRepository.save(discount);
    }

    public Discount getDiscountById(Integer discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new RuntimeException("Discount not found with ID: " + discountId));
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public void deleteDiscount(Integer discountId) {
        discountRepository.deleteById(discountId);
    }
}