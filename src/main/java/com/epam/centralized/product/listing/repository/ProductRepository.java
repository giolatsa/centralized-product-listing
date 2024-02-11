package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.Product;
import java.util.List;

import com.epam.centralized.product.listing.model.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByProductCategoryCategoryNameAndProductStatus(String categoryName, ProductStatus productStatus);

  List<Product> findAllByCompany(Company company);

  List<Product> findAllByProductStatus(ProductStatus productStatus);
}
