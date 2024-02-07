package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.ProductCategory;
import java.util.List;

public interface ProductCategoryService {
  List<ProductCategory> getAllCategories();
}
