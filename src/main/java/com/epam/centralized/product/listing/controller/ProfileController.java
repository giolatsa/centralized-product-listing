package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.service.CompanyService;
import com.epam.centralized.product.listing.service.OrderService;
import com.epam.centralized.product.listing.service.ProductService;
import com.epam.centralized.product.listing.service.UserService;
import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

  private final UserService userService;

  private final OrderService orderService;

  private final CompanyService companyService;

  private final ProductService productService;

  @GetMapping
  public String profile(Principal principal, Model model) {
    String email = principal.getName();

    User userDetails = userService.findByEmail(email);
    model.addAttribute("user", userDetails);

    Boolean hasCompany = companyService.userHasCompany(userDetails.getId());
    model.addAttribute("hasCompany", hasCompany);

    model.addAttribute("section", "details");

    return "profile";
  }

  @PostMapping("/update")
  public String updateProfile(@ModelAttribute User user, Principal principal, Model model) {

    System.out.println(user);

    User userDetails = userService.updateUserById(user, user.getId());

    Boolean hasCompany = companyService.userHasCompany(userDetails.getId());
    model.addAttribute("hasCompany", hasCompany);

    model.addAttribute("user", userDetails);

    model.addAttribute("section", "details");

    return "profile";
  }

  @GetMapping("/orders")
  public String showOrders(Model model, Principal principal) {
    String email = principal.getName();
    User userDetails = userService.findByEmail(email);
    List<Order> allOrdersByUserEmail = orderService.findAllOrdersByUserEmail(email);

    Boolean hasCompany = companyService.userHasCompany(userDetails.getId());
    model.addAttribute("hasCompany", hasCompany);

    model.addAttribute("user", userDetails);
    model.addAttribute("orders", allOrdersByUserEmail);

    model.addAttribute("section", "orders");

    return "profile";
  }

  @GetMapping("/company")
  public String getCompanyDetails(Principal principal, Model model) {
    Company company = companyService.findByUserEmail(principal.getName());
    User userDetails = userService.findByEmail(principal.getName());

    Boolean hasCompany = companyService.userHasCompany(userDetails.getId());

    List<Product> products = productService.findAllProductsByCompany(company);
    model.addAttribute("products", products);

    model.addAttribute("hasCompany", hasCompany);

    model.addAttribute("company", company);
    model.addAttribute("section", "company");
    model.addAttribute("user", userDetails);
    return "profile";
  }

  @GetMapping("/password")
  public String showPasswordForm(Model model, Principal principal) {
    String email = principal.getName();
    User userDetails = userService.findByEmail(email);

    Boolean hasCompany = companyService.userHasCompany(userDetails.getId());
    model.addAttribute("hasCompany", hasCompany);
    model.addAttribute("user", userDetails);
    model.addAttribute("section", "password");

    return "profile";
  }

  @PostMapping("/password/change")
  public String changeUserPassword(
      Principal principal,
      @RequestParam("currentPassword") String currentPassword,
      @RequestParam("newPassword") String newPassword,
      @RequestParam("confirmNewPassword") String confirmNewPassword,
      RedirectAttributes redirectAttributes,
      Model model) {
    // Retrieve the logged-in user
    String username = principal.getName();
    User user = userService.findByEmail(username);

    model.addAttribute("user", user);

    // Check if current password is correct and new passwords match
    if (!userService.checkIfValidOldPassword(user, currentPassword)) {
      redirectAttributes.addFlashAttribute("error", "Current password is incorrect.");
      return "redirect:/profile/password";
    }

    if (!newPassword.equals(confirmNewPassword)) {
      redirectAttributes.addFlashAttribute("error", "New passwords do not match.");
      return "redirect:/profile/password";
    }

    // Proceed with changing the password
    userService.changeUserPassword(user, newPassword);
    redirectAttributes.addFlashAttribute("success", "Password successfully changed.");
    return "redirect:/profile";
  }

  @GetMapping("/product/add")
  public String showAddProductModal(Model model, Principal principal) {

    Company company = companyService.findByUserEmail(principal.getName());
    User userDetails = userService.findByEmail(principal.getName());

    Boolean hasCompany = companyService.userHasCompany(userDetails.getId());

    List<Product> products = productService.findAllProductsByCompany(company);
    model.addAttribute("products", products);

    model.addAttribute("hasCompany", hasCompany);

    model.addAttribute("company", company);
    model.addAttribute("section", "company");
    model.addAttribute("user", userDetails);
    model.addAttribute("showAddProductModal", true);
    model.addAttribute("productCategories", productService.findAllProductCategories());
    return "profile";
  }

  @PostMapping("/product/add")
  public String addProduct(@ModelAttribute Product product, Principal principal) {
    productService.createProduct(product, principal.getName());
    return "redirect:/profile/company";
  }

  @PostMapping("/product/delete")
  public String deleteProduct(@RequestParam("productId") Long productId, Principal principal) {
    String username = principal.getName();
    productService.deleteProduct(productId, username);
    return "redirect:/profile/company";
  }
}
