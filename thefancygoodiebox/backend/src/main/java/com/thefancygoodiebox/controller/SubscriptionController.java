package com.thefancygoodiebox.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SubscriptionController {

    @GetMapping("/subscriptions")
    public List<Map<String, Object>> getSubscriptions() {
        return List.of(
                Map.of(
                        "id", 1,
                        "name", "Goodie Abo Basic",
                        "description", "Einmal im Monat 3 Überraschungsprodukte",
                        "price", 19.99,
                        "imageUrl", "/images/abo-basic.svg"
                ),
                Map.of(
                        "id", 2,
                        "name", "Goodie Abo Premium",
                        "description", "Einmal im Monat 5 Premiumprodukte",
                        "price", 29.99,
                        "imageUrl", "/images/abo-premium.svg"
                ),
                Map.of(
                        "id", 3,
                        "name", "Goodie Abo Deluxe",
                        "description", "Einmal im Monat 7 Premiumprodukte + Überraschung",
                        "price", 39.99,
                        "imageUrl", "/images/abo-deluxe.svg"
                )
        );
    }
}

