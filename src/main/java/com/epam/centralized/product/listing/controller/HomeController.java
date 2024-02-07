package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

  private final ProductService productService;

  public HomeController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/home")
  public String homePage(Model model) {
    List<Product> products = productService.getAllProducts();
    model.addAttribute("products", products);
    return "home";
  }

}
