package com.thefancygoodiebox.controller;

import com.thefancygoodiebox.model.Subscription;
import com.thefancygoodiebox.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")  // ← /subscriptions
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /** EINZIGE GET-Methode – filtert nach Category/Preis */
    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        List<Subscription> result = subscriptionService.getFiltered(category, minPrice, maxPrice);
        return ResponseEntity.ok(result);  // ← Entity direkt (kein DTO nötig, Jackson mapped)
    }

    @PostMapping
    public Subscription createSubscription(@RequestBody Subscription subscription) {
        return subscriptionService.saveSubscription(subscription);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
