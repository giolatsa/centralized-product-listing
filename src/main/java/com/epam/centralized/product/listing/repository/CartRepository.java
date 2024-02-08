package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Cart;
import com.epam.centralized.product.listing.model.enums.CartStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByUserEmailAndCartStatus(String email, CartStatus cartStatus);
}
