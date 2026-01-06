package com.thefancygoodiebox.strategy;

import com.thefancygoodiebox.model.Cart;
import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal calculateDiscount(Cart cart);
}

