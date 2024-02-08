package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
