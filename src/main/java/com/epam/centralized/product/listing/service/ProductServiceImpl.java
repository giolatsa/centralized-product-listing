package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.exception.CompanyNotFoundException;
import com.epam.centralized.product.listing.exception.NotProductOwnerException;
import com.epam.centralized.product.listing.exception.ProductNotFoundException;
import com.epam.centralized.product.listing.exception.UserNotFoundException;
import com.epam.centralized.product.listing.model.*;
import com.epam.centralized.product.listing.model.enums.CartStatus;
import com.epam.centralized.product.listing.model.enums.ProductStatus;
import com.epam.centralized.product.listing.repository.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final CartRepository cartRepository;

  private final ProductCategoryRepository productCategoryRepository;

  private final UserRepository userRepository;

  private final CompanyRepository companyRepository;

  @Override
  public List<Product> getAllProducts(String username) {
    List<Product> allProducts = productRepository.findAllByProductStatus(ProductStatus.ACTIVE);

    markProductsInCart(username, allProducts);
    return allProducts.stream()
        .sorted((p1, p2) -> p2.getCreateDate().compareTo(p1.getCreateDate()))
        .toList();
  }

  @Override
  public List<Product> getProductsByCategory(String categoryName, String username) {
    List<Product> productsByCategory =
        productRepository.findByProductCategoryCategoryNameAndProductStatus(
            categoryName, ProductStatus.ACTIVE);
    markProductsInCart(username, productsByCategory);
    return productsByCategory.stream()
        .sorted((p1, p2) -> p2.getCreateDate().compareTo(p1.getCreateDate()))
        .toList();
  }

  @Override
  public List<Product> searchProductsByNameOrDescription(String query, String username) {
    List<Product> filteredProducts = productRepository.findAll();

    // filter products by query
    List<Product> finalFilteredProducts =
        filteredProducts.stream()
            .filter(
                p ->
                    p.getName().toLowerCase().contains(query.toLowerCase())
                        || p.getDescription().toLowerCase().contains(query.toLowerCase()))
            .toList();

    markProductsInCart(username, finalFilteredProducts);
    return finalFilteredProducts.stream()
        .sorted((p1, p2) -> p2.getCreateDate().compareTo(p1.getCreateDate()))
        .toList();
  }

  @Override
  public List<Product> getProductsInCart(String username) {
    Cart cart =
        cartRepository
            .findByUserEmailAndCartStatus(username, CartStatus.ACTIVE)
            .orElse(Cart.builder().products(List.of()).build());

    return cart.getProducts();
  }

  @Override
  public List<Product> findAllProductsByCompany(Company company) {
    return productRepository.findAllByCompany(company).stream()
        .sorted((p1, p2) -> p2.getCreateDate().compareTo(p1.getCreateDate()))
        .toList();
  }

  @Override
  public List<ProductCategory> findAllProductCategories() {

    return productCategoryRepository.findAll();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Product createProduct(Product product, String username) {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    Company company =
        companyRepository
            .findByUser(user)
            .orElseThrow(() -> new CompanyNotFoundException("Company not found"));

    product.setCompany(company);
    product.setProductStatus(ProductStatus.ACTIVE);
    product.setCreateDate(LocalDateTime.now());

    return productRepository.save(product);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteProduct(Long productId, String username) {
    User user =
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    // make sure user is owner of the product
    if (!product.getCompany().getUser().equals(user)) {
      throw new NotProductOwnerException("User is not the owner of the product");
    }
    product.setProductStatus(ProductStatus.DISABLED);
    product.setUpdateDate(LocalDateTime.now());

    productRepository.save(product);
  }

  private List<Product> markProductsInCart(String username, List<Product> products) {
    cartRepository
        .findByUserEmailAndCartStatus(username, CartStatus.ACTIVE)
        .ifPresentOrElse(
            cart -> {
              // if cart is present, mark products in cart
              List<Product> productsInCart = cart.getProducts();
              products.forEach(
                  p -> p.setInCart(productsInCart.contains(p)));
            },
            // if cart is not present, mark all products as not in cart
            () -> products.forEach(p -> p.setInCart(false)));

    return products;
  }
}
