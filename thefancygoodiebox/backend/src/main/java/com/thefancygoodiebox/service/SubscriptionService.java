package com.thefancygoodiebox.service;

import com.thefancygoodiebox.model.Subscription;
import com.thefancygoodiebox.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public List<Subscription> getSubscriptionsByCategory(String category) {
        return subscriptionRepository.findByCategory(category);
    }

    // EINZELNE Price-Methode (sortiert)
    public List<Subscription> getSubscriptionsByPriceRange(BigDecimal min, BigDecimal max) {
        return subscriptionRepository.findByPriceBetweenOrderByPriceAsc(min, max);
    }

    /** NEU: Kombinierte Filter-Methode für Controller */
    public List<Subscription> getFiltered(String category, BigDecimal minPrice, BigDecimal maxPrice) {

        List<Subscription> subs = getAllSubscriptions();

        if (category != null && !category.trim().isEmpty() && !"all".equalsIgnoreCase(category)) {
            subs = subs.stream()
                    .filter(s -> s.getCategory().equalsIgnoreCase(category))
                    .toList();
        }

        if (minPrice != null) {
            subs = subs.stream()
                    .filter(s -> s.getPrice().compareTo(minPrice) >= 0)
                    .toList();
        }

        if (maxPrice != null) {
            subs = subs.stream()
                    .filter(s -> s.getPrice().compareTo(maxPrice) <= 0)
                    .toList();
        }

        return subs;
    }


    public Subscription saveSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }
}
