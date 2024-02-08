package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.service.CartService;
import com.epam.centralized.product.listing.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String cart(Model model, Principal principal,
                       @RequestParam(value = "showCheckoutModal", required = false, defaultValue = "false") boolean showCheckoutModal) {
        String username = principal.getName();
        List<Product> products = productService.getProductsInCart(username);
        Double total = products.stream().mapToDouble(Product::getPrice).sum();

        model.addAttribute("products", products);
        model.addAttribute("total", total);
        model.addAttribute("productCount", products.size());
        model.addAttribute("showCheckoutModal", showCheckoutModal);

        System.out.println(showCheckoutModal);

        return "cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId, Principal principal) {
        String username = principal.getName(); // Get logged-in username
        cartService.addProductToCart(username, productId);
        return "redirect:/home";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") Long productId, Principal principal) {
        String username = principal.getName(); // Get logged-in username
        cartService.removeProductFromCart(username, productId);
        return "redirect:/cart";
    }


}
