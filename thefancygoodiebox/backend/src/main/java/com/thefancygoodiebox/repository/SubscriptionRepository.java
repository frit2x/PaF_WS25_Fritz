package com.thefancygoodiebox.repository;

import com.thefancygoodiebox.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByCategory(String category);
    List<Subscription> findByPriceBetweenOrderByPriceAsc(BigDecimal min, BigDecimal max);
}
