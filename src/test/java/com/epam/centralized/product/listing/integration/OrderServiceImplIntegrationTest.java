package com.epam.centralized.product.listing.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.repository.OrderRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import com.epam.centralized.product.listing.service.OrderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class OrderServiceImplIntegrationTest {

  @Autowired private OrderService orderService;

  @Autowired private UserRepository userRepository;

  @Autowired private OrderRepository orderRepository;

  @BeforeEach
  void setup() {
    // Setup test data
    User user = new User();
    user.setEmail("test@example.com");
    user = userRepository.save(user);

    Order order = new Order();
    order.setUser(user);
    orderRepository.save(order);
  }

  @Test
  void findAllOrdersByUserEmail_ReturnsOrders() {
    List<Order> orders = orderService.findAllOrdersByUserEmail("test@example.com");
    assertFalse(orders.isEmpty(), "Should return at least one order");
    assertEquals(
        "test@example.com",
        orders.get(0).getUser().getEmail(),
        "Order should belong to the correct user");
  }
}
