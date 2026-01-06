package com.thefancygoodiebox.service;

import com.thefancygoodiebox.model.*;
import com.thefancygoodiebox.repository.UserRepository;
import com.thefancygoodiebox.strategy.DiscountStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CartService {

    @Autowired
    private UserRepository userRepository;

    public CheckoutResult checkout(String username, DiscountStrategy strategy) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = user.getCart();
        BigDecimal total = cart.getSubscriptions().stream()
                .map(Subscription::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal discountAmount = strategy.calculateDiscount(cart);
        BigDecimal finalPrice = total.subtract(discountAmount);

        return new CheckoutResult(finalPrice, discountAmount);
    }
}
