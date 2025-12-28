package com.thefancygoodiebox.service;

import com.thefancygoodiebox.model.Subscription;
import com.thefancygoodiebox.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public List<Subscription> findAll() {
        return repository.findAll();
    }
}
