package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}
