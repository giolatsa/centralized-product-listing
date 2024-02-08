package com.epam.centralized.product.listing.service;

public interface CartService {

  void addProductToCart(String username, Long productId);

  void removeProductFromCart(String username, Long productId);

  void checkout(String username);
}
