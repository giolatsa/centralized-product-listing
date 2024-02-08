package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Principal principal, Model model) {
        String email = principal.getName();
        User userDetails = userService.findByEmail(email);

        model.addAttribute("user",userDetails );


        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User user, Principal principal, Model model) {

        System.out.println(user);

        User userDetails = userService.updateUserById(user,user.getId());

        model.addAttribute("user",userDetails );


        return "profile";
    }

}
