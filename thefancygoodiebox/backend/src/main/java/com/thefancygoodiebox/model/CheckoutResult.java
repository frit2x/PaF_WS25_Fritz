package com.thefancygoodiebox.model;

import java.math.BigDecimal;

public class CheckoutResult {
    private BigDecimal totalPrice;
    private BigDecimal discount;

    public CheckoutResult(BigDecimal totalPrice, BigDecimal discount) {
        this.totalPrice = totalPrice;
        this.discount = discount;
    }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public BigDecimal getDiscount() { return discount; }
}
