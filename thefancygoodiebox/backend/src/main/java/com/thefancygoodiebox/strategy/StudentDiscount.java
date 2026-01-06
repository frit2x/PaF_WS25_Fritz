package com.thefancygoodiebox.strategy;

import com.thefancygoodiebox.model.Cart;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import com.thefancygoodiebox.strategy.DiscountStrategy;
import com.thefancygoodiebox.model.Subscription;

public class StudentDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(Cart cart) {
        BigDecimal totalDiscount = BigDecimal.ZERO;

        for (Subscription sub : cart.getSubscriptions()) {
            String category = sub.getCategory().toLowerCase();
            BigDecimal price = sub.getPrice();

            // Kategorien-spezifische Rabatte!
            switch (category) {
                case "food":
                    totalDiscount = totalDiscount.add(price.multiply(BigDecimal.valueOf(0.30))); // 30% Food
                    break;
                case "office":
                    totalDiscount = totalDiscount.add(price.multiply(BigDecimal.valueOf(0.20))); // 20% Office
                    break;
                case "nerd":
                    totalDiscount = totalDiscount.add(price.multiply(BigDecimal.valueOf(0.10))); // 10% Nerd
                    break;
            }
        }
        return totalDiscount;
    }
}


