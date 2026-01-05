package com.thefancygoodiebox.controller;

import com.thefancygoodiebox.model.Subscription;
import com.thefancygoodiebox.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/subscriptions")      // Basis-URL für alle Subscription-Endpunkte
@CrossOrigin(origins = "http://localhost:4200") // Erlaubt Aufrufe vom Angular-Frontend (localhost:4200)
public class SubscriptionController {

    // Service-Layer für Geschäftslogik und DB-Zugriffe
    private final SubscriptionService subscriptionService;

    // Konstruktor-Injection für bessere Testbarkeit und Immutability
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * GET /subscriptions
     * Optionales Filtern nach Kategorie und Preisbereich.
     * Wenn keine Filter übergeben werden, liefert der Service alle Subscriptions zurück.
     */
    @GetMapping
    public ResponseEntity<List<Subscription>> getSubscriptions(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        // Delegiert die Filterlogik an den Service; Controller bleibt schlank
        List<Subscription> result = subscriptionService.getFiltered(category, minPrice, maxPrice);

        // 200 OK mit der Ergebnisliste im Response-Body (JSON durch Jackson)
        return ResponseEntity.ok(result);
    }

    /**
     * POST /subscriptions
     * Legt eine neue Subscription an.
     * Erwartet eine Subscription im Request-Body (JSON → Subscription via Jackson).
     */
    @PostMapping
    public Subscription createSubscription(@RequestBody Subscription subscription) {
        // Persistiert die Subscription und gibt das gespeicherte Objekt zurück
        // (inkl. generierter ID, falls vorhanden)
        return subscriptionService.saveSubscription(subscription);
    }

    /**
     * DELETE /subscriptions/{id}
     * Löscht eine Subscription anhand ihrer ID.
     * Gibt bei Erfolg 204 No Content zurück.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        // Löschen der Subscription; Fehlerhandling (z.B. falls ID nicht existiert)
        // wird idealerweise im Service gekapselt
        subscriptionService.deleteSubscription(id);

        // 204 No Content, da kein Body zurückgegeben werden muss
        return ResponseEntity.noContent().build();
    }
}