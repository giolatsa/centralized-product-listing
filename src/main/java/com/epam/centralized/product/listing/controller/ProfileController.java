package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Order;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.service.OrderService;
import com.epam.centralized.product.listing.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/password")
    public String showPasswordForm(Model model,Principal principal) {
        String email = principal.getName();
        User userDetails = userService.findByEmail(email);

        model.addAttribute("user",userDetails );
        model.addAttribute("section", "password");

        return "profile";
    }

    @PostMapping("/password/change")
    public String changeUserPassword(Principal principal, @RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmNewPassword") String confirmNewPassword,
                                     RedirectAttributes redirectAttributes, Model model) {
        // Retrieve the logged-in user
        String username = principal.getName();
        User user = userService.findByEmail(username);

        model.addAttribute("user",user );

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


}
