package com.thefancygoodiebox.repository;

import com.thefancygoodiebox.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

/**
 * Repository Pattern Implementierung: Datenbank-Abstraktion für Subscription-Entity
 * Spring Data JPA generiert AUTOMATISCH alle SQL-Statements aus Methodennamen!
 */
@Repository  // Spring Bean: Wird automatisch im Service injected
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * GENERIERT: SELECT * FROM subscription WHERE category = ?1
     * Methodenname → JPA → SQL WHERE category = ?
     */
    List<Subscription> findByCategory(String category);

    /**
     * GENERIERT: SELECT * FROM subscription WHERE price BETWEEN ?1 AND ?2 ORDER BY price ASC
     * findBy + Property + Between + OrderBy → perfekte SQL-Abfrage!
     */
    List<Subscription> findByPriceBetweenOrderByPriceAsc(BigDecimal min, BigDecimal max);
}

