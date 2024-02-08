package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {}
