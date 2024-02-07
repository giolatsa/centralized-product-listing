package com.epam.centralized.product.listing.repository;

import com.epam.centralized.product.listing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
