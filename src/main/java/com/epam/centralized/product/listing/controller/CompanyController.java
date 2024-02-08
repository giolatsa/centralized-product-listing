package com.epam.centralized.product.listing.controller;

import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping
    public String getCompany() {
        return "company";
    }


    @PostMapping("/create")
    public String createCompany(Company company , Model model, Principal principal) {

        companyService.createCompany(company,principal.getName());


        return "redirect:/profile";
    }

    @PostMapping("/update")
    public String updateCompanyDetails(Company company, Model model, Principal principal) {
        companyService.updateCompany(company,principal.getName());
        return "redirect:/profile/company";
    }
}
