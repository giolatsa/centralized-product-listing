package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Product;
import java.util.List;

public interface ProductService {

  List<Product> getAllProducts();

  List<Product> getProductsByCategory(String categoryName);

    List<Product> searchProductsByNameOrDescription(String query);
}
