package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.exception.UserNameUsedException;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

  private final UserService userService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @PostMapping("/register/user")
  public String registerUser(User user, RedirectAttributes redirectAttributes) {
    try {
      userService.createUser(user);
    } catch (UserNameUsedException e) {
      // Add the exception message to redirect attributes to show it on the registration page
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/register"; // Redirect back to the registration page
    }
    return "redirect:/login";
  }
}
