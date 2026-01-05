package com.thefancygoodiebox.service;

import com.thefancygoodiebox.model.Subscription;
import com.thefancygoodiebox.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

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

        // Intelligente Filter-Kombination mit Repository-Methoden
        if (category != null && !category.trim().isEmpty()) {
            // Filter 1: Nur Kategorie → DB WHERE category = ?
            List<Subscription> result = subscriptionRepository.findByCategory(category);

            // UND Preisbereich
            if (minPrice != null || maxPrice != null) {
                BigDecimal effectiveMin = minPrice != null ? minPrice : BigDecimal.ZERO;
                BigDecimal effectiveMax = maxPrice != null ? maxPrice : new BigDecimal("999999");
                result.removeIf(sub -> sub.getPrice().compareTo(effectiveMin) < 0 ||
                        sub.getPrice().compareTo(effectiveMax) > 0);
            }
            return result;
        }

        // Nur Preisbereich
        if (minPrice != null || maxPrice != null) {
            BigDecimal effectiveMin = minPrice != null ? minPrice : BigDecimal.ZERO;
            BigDecimal effectiveMax = maxPrice != null ? maxPrice : new BigDecimal("999999");
            return subscriptionRepository.findByPriceBetweenOrderByPriceAsc(effectiveMin, effectiveMax);
        }

        // Keine Filter → alle
        return subscriptionRepository.findAll();
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
