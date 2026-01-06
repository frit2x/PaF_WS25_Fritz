package com.thefancygoodiebox.strategy;

import com.thefancygoodiebox.model.Cart;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

public class NoDiscount implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(Cart cart) {
        return BigDecimal.ZERO;
    }
}
