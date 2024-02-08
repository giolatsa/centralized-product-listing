package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByProductCategoryCategoryName(String categoryName);

    List<Product> findAllByCompany(Company company);
}
