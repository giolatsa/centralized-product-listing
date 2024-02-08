package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Cart;
import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.CartStatus;
import com.epam.centralized.product.listing.repository.CartRepository;
import com.epam.centralized.product.listing.repository.OrderRepository;
import com.epam.centralized.product.listing.repository.ProductRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;

  private final ProductRepository productRepository;

  private final UserRepository userRepository;

  private final OrderRepository orderRepository;

  public CartServiceImpl(
      CartRepository cartRepository,
      ProductRepository productRepository,
      UserRepository userRepository,
      OrderRepository orderRepository) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.userRepository = userRepository;
    this.orderRepository = orderRepository;
  }

  @Override
  public void addProductToCart(String username, Long productId) {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    cartRepository
        .findByUserEmailAndCartStatus(username, CartStatus.ACTIVE)
        .ifPresentOrElse(
            // if cart is present, add product to cart
            cart -> {
              cart.getProducts().add(product);
              cart.setUpdateDate(LocalDateTime.now());
              cartRepository.save(cart);
            },
            // if cart is not present, create a new cart and add product to cart
            () -> {
              Cart cart =
                  Cart.builder()
                      .cartStatus(CartStatus.ACTIVE)
                      .products(List.of(product))
                      .user(user)
                      .createDate(LocalDateTime.now())
                      .build();
              cartRepository.save(cart);
            });
  }

  @Override
  public void removeProductFromCart(String username, Long productId) {
    Cart cart =
        cartRepository
            .findByUserEmailAndCartStatus(username, CartStatus.ACTIVE)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    cart.getProducts().remove(product);
    cart.setUpdateDate(LocalDateTime.now());
    cartRepository.save(cart);
  }

  @Override
  public void checkout(String username) {
    // find user's active cart
    Cart cart =
        cartRepository
            .findByUserEmailAndCartStatus(username, CartStatus.ACTIVE)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
    // set cart status to bought and update date
    cart.setCartStatus(CartStatus.BOUGHT);
    cart.setUpdateDate(LocalDateTime.now());
    cartRepository.save(cart);

    // create order
    Order order =
        Order.builder()
            .user(cart.getUser())
            .cart(cart)
            .createDate(LocalDateTime.now())
            .totalPrice(cart.getProducts().stream().mapToDouble(Product::getPrice).sum())
            .totalQuantity(cart.getProducts().size())
            .build();

    orderRepository.save(order);
  }
}
