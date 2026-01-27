package com.thefancygoodiebox.controller;

import com.thefancygoodiebox.model.*;
import com.thefancygoodiebox.repository.*;
import com.thefancygoodiebox.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestParam String username, @RequestParam Long subscriptionId) {
        try {
            Cart cart = cartRepository.findByUserUsername(username);
            if (cart != null) {
                // Check OB wirklich drin
                boolean alreadyIn = cart.getSubscriptions().stream()
                        .anyMatch(s -> s.getId().equals(subscriptionId));
                if (alreadyIn) {
                    return ResponseEntity.ok(Map.of("status", "already", "message", "Already in cart"));
                }
            }

            User user = userRepository.findByUsername(username).orElseThrow();
            if (cart == null) {
                cart = new Cart();
                cart.setUser(user);
                cartRepository.save(cart);
            }
            Subscription sub = subscriptionRepository.findById(subscriptionId).orElseThrow();
            cart.addSubscription(sub);
            cartRepository.save(cart);

            System.out.println("✅ ADDED " + sub.getName());
            return ResponseEntity.ok(Map.of("status", "success"));
        } catch (Exception e) {
            System.err.println("❌ " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }






    @GetMapping
    public ResponseEntity<Map<String, Object>> getCart(@RequestParam String username) {
        try {
            Cart cart = cartRepository.findByUserUsername(username);
            List<Map<String, Object>> items = new ArrayList<>();

            if (cart != null && cart.getSubscriptions() != null) {
                items = cart.getSubscriptions().stream()
                        .map(s -> {
                            Map<String, Object> item = new HashMap<>();
                            item.put("id", s.getId());
                            item.put("name", s.getName());
                            item.put("price", s.getPrice().doubleValue());
                            return item;
                        })
                        .collect(Collectors.toList());
            }

            Map<String, Object> response = new HashMap<>();
            response.put("itemCount", items.size());
            response.put("items", items);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> empty = new HashMap<>();
            empty.put("itemCount", 0);
            empty.put("items", new ArrayList<>());
            return ResponseEntity.ok(empty);
        }
    }

    @GetMapping("/preview")
    public ResponseEntity<Map<String, Object>> preview(@RequestParam String username,
                                                       @RequestParam(required = false) String strategy) {  // Optional

        System.out.println("PREVIEW: " + username);

        Cart cart = cartRepository.findByUserUsername(username);  // ← cart IMMER hier!
        System.out.println("Cart: " + (cart != null ? cart.getSubscriptions().size() : "NULL"));

        // Immer leere Response als Basis
        Map<String, Object> response = new HashMap<>();
        response.put("subtotal", 0.0);
        response.put("discount", 0.0);
        response.put("total", 0.0);
        response.put("strategy", username.toLowerCase());  // student oder admin

        if (cart == null || cart.getSubscriptions() == null || cart.getSubscriptions().isEmpty()) {
            return ResponseEntity.ok(response);  // ← FRÜH raus!
        }

        // Strategy nach USERNAME (nicht Param!)
        DiscountStrategy discountStrategy = "student".equals(username.toLowerCase())
                ? new StudentDiscount() : new NoDiscount();

        BigDecimal subtotal = cart.getSubscriptions().stream()  // ← cart SCOPE OK!
                .map(Subscription::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discount = discountStrategy.calculateDiscount(cart);
        BigDecimal total = subtotal.subtract(discount);

        response.put("subtotal", subtotal.doubleValue());
        response.put("discount", discount.doubleValue());
        response.put("total", total.doubleValue());

        return ResponseEntity.ok(response);
    }



    // === NEU: HIER HINZUFÜGEN ===
    @GetMapping("/items")
    public ResponseEntity<List<Map<String, Object>>> getCartItems(@RequestParam String username) {
        try {
            Cart cart = cartRepository.findByUserUsername(username);
            List<Map<String, Object>> items = new ArrayList<>();

            if (cart != null && cart.getSubscriptions() != null) {
                items = cart.getSubscriptions().stream()
                        .map(s -> {
                            Map<String, Object> item = new HashMap<>();
                            item.put("id", s.getId());
                            item.put("name", s.getName());
                            item.put("price", s.getPrice().doubleValue());
                            return item;
                        })
                        .collect(Collectors.toList());
            }

            return ResponseEntity.ok(items);  // ← Array von Items!
        } catch (Exception e) {
            return ResponseEntity.ok(new ArrayList<>());  // Leere Liste bei Fehler
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearCart(@RequestParam String username) {
        try {
            Cart cart = cartRepository.findByUserUsername(username);
            if (cart != null) {
                cart.getSubscriptions().clear();  // Alle Items löschen
                cartRepository.save(cart);
            }
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", "Cart cleared"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }



}
