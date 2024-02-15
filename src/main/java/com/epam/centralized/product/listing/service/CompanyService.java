package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Company;
import java.util.Optional;

public interface CompanyService {

  Company createCompany(Company company, String username);

  Boolean userHasCompany(Long id);

  Optional<Company> findByUserEmail(String name);

  Company updateCompany(Company company, String email);
}
