package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.service.OrderService;
import com.epam.centralized.product.listing.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final OrderService orderService;

    public ProfileController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String profile(Principal principal, Model model) {
        String email = principal.getName();
        User userDetails = userService.findByEmail(email);

        model.addAttribute("user",userDetails );

        model.addAttribute("section", "details");

        return "profile";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User user, Principal principal, Model model) {

        System.out.println(user);

        User userDetails = userService.updateUserById(user,user.getId());

        model.addAttribute("user",userDetails );

        model.addAttribute("section", "details");

        return "profile";
    }

    @GetMapping("/orders")
    public String showOrders(Model model , Principal principal) {
        String email = principal.getName();
        User userDetails = userService.findByEmail(email);
        List<Order> allOrdersByUserEmail = orderService.findAllOrdersByUserEmail(email);

        model.addAttribute("user",userDetails );
        model.addAttribute("orders",allOrdersByUserEmail);

        model.addAttribute("section", "orders");

        return "profile";

    }



}
