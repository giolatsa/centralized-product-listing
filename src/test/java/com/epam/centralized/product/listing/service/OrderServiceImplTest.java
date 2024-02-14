package com.epam.centralized.product.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.repository.OrderRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrderServiceImplTest {

  @Mock private OrderRepository orderRepository;

  @Mock private UserRepository userRepository;

  @InjectMocks private OrderServiceImpl orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAllOrdersByUserEmail_UserExists_ReturnsUserOrders() {
    // Arrange
    String email = "user@example.com";
    User user = new User();
    user.setEmail(email);
    List<Order> expectedOrders = List.of(new Order());
    when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
    when(orderRepository.findAllByUser(user)).thenReturn(expectedOrders);

    // Act
    List<Order> result = orderService.findAllOrdersByUserEmail(email);

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(expectedOrders.size(), result.size());
    verify(userRepository, times(1)).findByEmail(email);
    verify(orderRepository, times(1)).findAllByUser(user);
  }

  @Test
  void findAllOrdersByUserEmail_UserNotFound_ThrowsRuntimeException() {
    // Arrange
    String email = "nonexistent@example.com";
    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    // Act & Assert
    Exception exception =
        assertThrows(RuntimeException.class, () -> orderService.findAllOrdersByUserEmail(email));
    assertEquals("User not found", exception.getMessage());
    verify(userRepository, times(1)).findByEmail(email);
    verify(orderRepository, never()).findAllByUser(any(User.class));
  }
}
