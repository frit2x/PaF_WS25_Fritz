package com.thefancygoodiebox.repository;

import com.thefancygoodiebox.model.Cart;  // ← model!
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserUsername(String username);
}

