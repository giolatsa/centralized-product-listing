package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public List<Product> getProductsByCategory(String categoryName) {
    return productRepository.findByProductCategoryCategoryName(categoryName);
  }
}
