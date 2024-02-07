package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.service.ProductCategoryService;
import com.epam.centralized.product.listing.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home")
public class HomeController {

  private final ProductService productService;

  private final ProductCategoryService categoryService;

  public HomeController(ProductService productService, ProductCategoryService categoryService) {
    this.productService = productService;
    this.categoryService = categoryService;
  }

  @GetMapping
  public String homePage(Model model) {
    List<Product> products = productService.getAllProducts();
    model.addAttribute("products", products);
    model.addAttribute("categories", categoryService.getAllCategories());
    return "home";
  }

  @GetMapping("/category")
  public String getProductsByCategory(@RequestParam("category") String categoryName, Model model) {
    List<Product> products =
        "all".equals(categoryName)
            ? productService.getAllProducts()
            : productService.getProductsByCategory(categoryName);
    model.addAttribute("categories", categoryService.getAllCategories());

    model.addAttribute("products", products);
    return "home";
  }
}
