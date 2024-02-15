package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.ProductCategory;
import com.epam.centralized.product.listing.repository.ProductCategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService {

  private final ProductCategoryRepository productCategoryRepository;

  @Override
  public List<ProductCategory> getAllCategories() {

    return productCategoryRepository.findAll();
  }
}
