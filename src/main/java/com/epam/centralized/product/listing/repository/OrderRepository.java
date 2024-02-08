package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUser(User user);
}
