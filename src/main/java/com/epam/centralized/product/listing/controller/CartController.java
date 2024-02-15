package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.service.CartService;
import com.epam.centralized.product.listing.service.ProductService;
import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

  private final CartService cartService;

  private final ProductService productService;

  @GetMapping
  public String cart(
      Model model,
      Principal principal,
      @RequestParam(value = "showCheckoutModal", required = false, defaultValue = "false")
          boolean showCheckoutModal) {
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
    String username = principal.getName();
    cartService.addProductToCart(username, productId);
    return "redirect:/home";
  }

  @PostMapping("/remove")
  public String removeFromCart(@RequestParam("productId") Long productId, Principal principal) {
    String username = principal.getName();
    cartService.removeProductFromCart(username, productId);
    return "redirect:/cart";
  }

  @PostMapping("/checkout")
  public String checkout(Principal principal) {
    String username = principal.getName();
    cartService.checkout(username);
    return "redirect:/cart";
  }
}
