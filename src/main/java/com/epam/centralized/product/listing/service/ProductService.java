package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Company;
import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.model.ProductCategory;
import java.util.List;

public interface ProductService {

  List<Product> getAllProducts(String username);

  List<Product> getProductsByCategory(String categoryName, String username);

  List<Product> searchProductsByNameOrDescription(String query, String username);

  List<Product> getProductsInCart(String username);

  List<Product> findAllProductsByCompany(Company company);

  List<ProductCategory> findAllProductCategories();

  Product createProduct(Product product, String username);

  void deleteProduct(Long productId, String username);
}
