package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId, Principal principal) {
        String username = principal.getName(); // Get logged-in username
        cartService.addProductToCart(username, productId);
        return "redirect:/home"; // Redirect to a page of your choice
    }


}
