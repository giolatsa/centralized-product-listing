package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Company;
import java.util.Optional;

import com.epam.centralized.product.listing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
  Optional<Company> findByUserId(Long id);

    Optional<Company> findByUser(User user);
}
