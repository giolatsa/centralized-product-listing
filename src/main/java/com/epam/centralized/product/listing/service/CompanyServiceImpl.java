package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.CompanyStatus;
import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.repository.CompanyRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompanyServiceImpl implements CompanyService{

    private final CompanyRepository companyRepository;

    private final UserRepository userRepository;


    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Company createCompany(Company company,String username) {
        User user=userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));

        company.setUser(user);
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company.setRegisterDate(LocalDateTime.now());

        Company savedCompany = companyRepository.save(company);

        user.setUserRole(UserRole.COMPANY);
        userRepository.save(user);

        return savedCompany;
    }
}
