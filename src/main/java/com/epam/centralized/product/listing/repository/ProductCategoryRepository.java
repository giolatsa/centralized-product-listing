package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {}
