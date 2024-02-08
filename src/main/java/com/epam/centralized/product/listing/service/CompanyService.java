package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Company;

public interface CompanyService {

    Company createCompany(Company company,String username);


    Boolean userHasCompany(Long id);

    Company findByUserEmail(String name);

    Company updateCompany(Company company, String email);
}
