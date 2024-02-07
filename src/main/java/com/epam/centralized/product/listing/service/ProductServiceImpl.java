package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

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

  @Override
  public List<Product> searchProductsByNameOrDescription(String query) {
    List<Product> products = productRepository.findAll();
    return products.stream()
            .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()) ||
                    p.getDescription().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
  }
}
