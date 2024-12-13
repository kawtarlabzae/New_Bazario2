package com.example.New_bazario.controllers;

import com.example.New_bazario.entities.Discount;
import com.example.New_bazario.services.DiscountService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping
    public Discount addDiscount(@RequestParam Integer productId,
                                @RequestParam BigDecimal discountPercentage,
                                @RequestParam LocalDateTime startDate,
                                @RequestParam LocalDateTime endDate) {
        return discountService.addDiscount(productId, discountPercentage, startDate, endDate);
    }

    @GetMapping("/{id}")
    public Discount getDiscountById(@PathVariable Integer id) {
        return discountService.getDiscountById(id);
    }

    @GetMapping
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscounts();
    }

    @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable Integer id) {
        discountService.deleteDiscount(id);
    }
}