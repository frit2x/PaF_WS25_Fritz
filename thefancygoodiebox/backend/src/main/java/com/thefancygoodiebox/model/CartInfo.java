package com.thefancygoodiebox.model;

import java.math.BigDecimal;

public class CartInfo {
    public long itemCount;
    public BigDecimal subtotal;

    public CartInfo(long itemCount, BigDecimal subtotal) {
        this.itemCount = itemCount;
        this.subtotal = subtotal;
    }
}
