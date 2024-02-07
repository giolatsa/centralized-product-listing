package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Cart;
import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.CartStatus;
import com.epam.centralized.product.listing.repository.CartRepository;
import com.epam.centralized.product.listing.repository.ProductRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;


    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void addProductToCart(String username, Long productId) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        cartRepository.findByUserEmailAndCartStatus(username,CartStatus.ACTIVE).ifPresentOrElse(
                //if cart is present, add product to cart
            cart -> {
                cart.getProducts().add(product);
                cart.setUpdateDate(LocalDateTime.now());
                cartRepository.save(cart);
            },
                //if cart is not present, create a new cart and add product to cart
            () -> {
                Cart cart = Cart.builder()
                        .cartStatus(CartStatus.ACTIVE)
                        .products(List.of(product))
                        .user(user)
                        .createDate(LocalDateTime.now())
                        .build();
                cartRepository.save(cart);
            }
        );



    }
}
