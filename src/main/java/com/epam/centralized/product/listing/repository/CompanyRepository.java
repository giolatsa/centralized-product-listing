package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findByUserId(Long id);
}
