package com.epam.centralized.product.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.epam.centralized.product.listing.model.Cart;
import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.CartStatus;
import com.epam.centralized.product.listing.repository.CartRepository;
import com.epam.centralized.product.listing.repository.OrderRepository;
import com.epam.centralized.product.listing.repository.ProductRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CartServiceImplTest {

  @Mock private CartRepository cartRepository;

  @Mock private ProductRepository productRepository;

  @Mock private UserRepository userRepository;

  @Mock private OrderRepository orderRepository;

  @Mock private Cart cart;

  @InjectMocks private CartServiceImpl cartService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void addProductToCart_NoActiveCart_CreatesNewCartAndAddsProduct() {
    // Arrange
    String username = "user@example.com";
    Long productId = 1L;
    User user = new User();
    Product product = new Product();

    when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(cartRepository.findByUserEmailAndCartStatus(username, CartStatus.ACTIVE))
        .thenReturn(Optional.empty());

    // Act
    cartService.addProductToCart(username, productId);

    // Assert
    verify(cartRepository).save(any(Cart.class));
  }

  @Test
  void addProductToCart_ActiveCartExists_AddsProductToExistingCart() {
    // Arrange
    String username = "user@example.com";
    Long productId = 1L;
    User user = new User();
    Product product = new Product();
    Cart existingCart = new Cart();
    existingCart.setProducts(new ArrayList<>());

    when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(cartRepository.findByUserEmailAndCartStatus(username, CartStatus.ACTIVE))
        .thenReturn(Optional.of(existingCart));

    // Act
    cartService.addProductToCart(username, productId);

    // Assert
    verify(cartRepository).save(existingCart);
    assertTrue(existingCart.getProducts().contains(product));
  }

  @Test
  void removeProductFromCart_ProductAndCartExist_ProductRemoved() {
    // Arrange
    String username = "user@example.com";
    Long productId = 1L;
    User user = new User();
    Product product = new Product();
    Cart cart = new Cart();
    cart.setUser(user);
    cart.setProducts(new ArrayList<>(List.of(product)));

    when(cartRepository.findByUserEmailAndCartStatus(username, CartStatus.ACTIVE))
        .thenReturn(Optional.of(cart));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // Act
    cartService.removeProductFromCart(username, productId);

    // Assert
    verify(cartRepository).save(cart);
    assertFalse(cart.getProducts().contains(product), "Product should be removed from the cart");
  }

  @Test
  void checkout_UserHasActiveCart_CreatesOrderAndUpdatesCartStatus() {
    // Arrange
    String username = "user@example.com";
    User user = new User();
    Cart cart = new Cart();
    cart.setUser(user);
    Product product = new Product();
    product.setPrice(100.00);
    cart.setProducts(List.of(product));
    cart.setCartStatus(CartStatus.ACTIVE);

    when(cartRepository.findByUserEmailAndCartStatus(username, CartStatus.ACTIVE))
        .thenReturn(Optional.of(cart));

    // Act
    cartService.checkout(username);

    // Assert
    verify(cartRepository).save(cart);
    assertEquals(
        CartStatus.BOUGHT, cart.getCartStatus(), "Cart status should be updated to BOUGHT");
    verify(orderRepository).save(any(Order.class));
  }
}
