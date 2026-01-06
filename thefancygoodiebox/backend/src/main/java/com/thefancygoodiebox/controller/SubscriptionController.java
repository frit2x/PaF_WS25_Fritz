package com.thefancygoodiebox.controller;

import com.thefancygoodiebox.model.*;
import com.thefancygoodiebox.service.*;
import com.thefancygoodiebox.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscriptions")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final CartService cartService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService,
                                  CartService cartService) {
        this.subscriptionService = subscriptionService;
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        return ResponseEntity.ok(subscriptionService.getFiltered(category, minPrice, maxPrice));
    }

    @PostMapping
    public Subscription createSubscription(@RequestBody Subscription subscription) {
        return subscriptionService.saveSubscription(subscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ STRATEGY CHECKOUT 
    @GetMapping("/cart/checkout")
    public ResponseEntity<Map<String, Object>> checkout(
            @RequestParam String username,
            @RequestParam String strategy) {

        DiscountStrategy discountStrategy = "student".equals(strategy.toLowerCase())
                ? new StudentDiscount() : new NoDiscount();

        CheckoutResult result = cartService.checkout(username, discountStrategy);

        return ResponseEntity.ok(Map.of(
                "total", result.getTotalPrice().doubleValue(),
                "discount", result.getDiscount().doubleValue(),
                "strategy", strategy
        ));
    }
}
