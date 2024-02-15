package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.exception.UserNotFoundException;
import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.User;
import com.epam.centralized.product.listing.model.enums.CompanyStatus;
import com.epam.centralized.product.listing.model.enums.UserRole;
import com.epam.centralized.product.listing.repository.CompanyRepository;
import com.epam.centralized.product.listing.repository.UserRepository;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;

  private final UserRepository userRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Company createCompany(Company company, String username) {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    company.setUser(user);
    company.setCompanyStatus(CompanyStatus.ACTIVE);
    company.setRegisterDate(LocalDateTime.now());

    Company savedCompany = companyRepository.save(company);

    user.setUserRole(UserRole.COMPANY);
    userRepository.save(user);

    return savedCompany;
  }

  @Override
  public Boolean userHasCompany(Long userId) {

    return companyRepository.findByUserId(userId).isPresent();
  }

  @Override
  public Company findByUserEmail(String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    return companyRepository.findByUserId(user.getId()).orElse(null);
  }

  @Override
  @Transactional
  public Company updateCompany(Company company, String email) {
    User user =
        userRepository
            .findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
    //Updates the company name and email /profile/company
    return companyRepository.save(company);
  }
}
