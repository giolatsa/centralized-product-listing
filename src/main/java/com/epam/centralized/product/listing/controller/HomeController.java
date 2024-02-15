package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.service.ProductCategoryService;
import com.epam.centralized.product.listing.service.ProductService;
import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

  private final ProductService productService;

  private final ProductCategoryService categoryService;


  @GetMapping
  public String homePage(Model model, Principal principal) {
    String username = principal.getName();

    List<Product> products = productService.getAllProducts(username);
    model.addAttribute("products", products);
    model.addAttribute("categories", categoryService.getAllCategories());
    return "home";
  }

  @GetMapping("/category")
  public String getProductsByCategory(
      @RequestParam("category") String categoryName, Model model, Principal principal) {
    String username = principal.getName();

    List<Product> products =
        "all".equals(categoryName)
            ? productService.getAllProducts(username)
            : productService.getProductsByCategory(categoryName, username);
    model.addAttribute("categories", categoryService.getAllCategories());

    model.addAttribute("products", products);
    return "home";
  }

  @GetMapping("/search")
  public String searchProducts(
      @RequestParam("query") String query, Model model, Principal principal) {
    String username = principal.getName();

    List<Product> matchingProducts =
        productService.searchProductsByNameOrDescription(query, username);
    model.addAttribute("products", matchingProducts);
    model.addAttribute("categories", categoryService.getAllCategories());

    return "home";
  }
}
