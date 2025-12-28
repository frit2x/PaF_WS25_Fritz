package com.thefancygoodiebox.controller;

import com.thefancygoodiebox.model.Subscription;
import com.thefancygoodiebox.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "http://localhost:4200") // Angular erlaubt Zugriff
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Subscription> getAll() {
        return service.findAll();
    }
}
