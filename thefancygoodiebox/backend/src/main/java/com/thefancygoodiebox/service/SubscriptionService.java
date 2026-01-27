package com.thefancygoodiebox.service;

import com.thefancygoodiebox.model.Subscription;
import com.thefancygoodiebox.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;

/**
 * Service-Layer: Enthält ALLE Geschäftslogik und DB-Zugriffe.
 * Controller delegiert hierher – hält Controller dünn und testbar.
 */
@Service  // Spring Bean – wird automatisch im Controller injected
public class SubscriptionService {

    // Repository für DB-CRUD-Operationen (Dependency Injection)
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    /**
     * Zentrale GET-Methode: Filtert Subscriptions nach Kategorie und Preisbereich.
     * Unterstützt beliebige Kombinationen von Parametern (null = kein Filter).
     */
    public List<Subscription> getFiltered(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        return subscriptionRepository.findAll().stream()
                .filter(s -> category == null || "all".equals(category) || category.equals(s.getCategory()))
                .filter(s -> minPrice == null || s.getPrice().compareTo(minPrice) >= 0)
                .filter(s -> maxPrice == null || s.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }

    /**
     * Speichert/updated eine Subscription.
     * Bei neuem Objekt (id=null) wird ID von DB generiert.
     */
    public Subscription saveSubscription(Subscription subscription) {
        // Einfache Validierung (kann erweitert werden)
        if (subscription.getName() == null || subscription.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name ist erforderlich");
        }
        if (subscription.getPrice() == null || subscription.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Preis muss >= 0 sein");
        }

        // Repository.save() macht INSERT oder UPDATE (smart!)
        return subscriptionRepository.save(subscription);
    }

    /**
     * Löscht Subscription per ID.
     * Falls ID nicht existiert → EmptyResultDataAccessException (vom Repository)
     */
    public void deleteSubscription(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new IllegalArgumentException("Subscription mit ID " + id + " nicht gefunden");
        }
        subscriptionRepository.deleteById(id);
    }
}
