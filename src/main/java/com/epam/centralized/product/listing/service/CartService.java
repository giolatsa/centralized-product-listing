package com.epam.centralized.product.listing.service;

public interface CartService {

    void addProductToCart(String username, Long productId);
}
