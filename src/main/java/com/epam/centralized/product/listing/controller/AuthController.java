package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
  public String registerUser(User user) {

    userService.createUser(user);

    return "redirect:/login";
  }
}
