package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.model.enums.ProductStatus;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByProductCategoryCategoryNameAndProductStatusOrderByCreateDateDesc(String categoryName, ProductStatus status);


  List<Product> findAllByCompanyOrderByCreateDateDesc(Company company);

  List<Product> findAllByProductStatusOrderByCreateDateDesc(ProductStatus productStatus);

  List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Sort sort);

}
