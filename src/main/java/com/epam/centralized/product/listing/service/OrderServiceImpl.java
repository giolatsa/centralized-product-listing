package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.repository.OrderRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final UserRepository userRepository;

  @Override
  public List<Order> findAllOrdersByUserEmail(String email) {
    User user =
        userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    return orderRepository.findAllByUser(user);
  }
}
