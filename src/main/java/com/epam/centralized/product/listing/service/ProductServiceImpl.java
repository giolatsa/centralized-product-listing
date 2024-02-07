package com.epam.centralized.product.listing.service;

import com.epam.centralized.product.listing.model.Product;
import com.epam.centralized.product.listing.model.enums.CartStatus;
import com.epam.centralized.product.listing.repository.CartRepository;
import com.epam.centralized.product.listing.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final CartRepository cartRepository;

  public ProductServiceImpl(ProductRepository productRepository, CartRepository cartRepository) {
    this.productRepository = productRepository;
    this.cartRepository = cartRepository;
  }

  @Override
  public List<Product> getAllProducts(String username) {
    List<Product> allProducts = productRepository.findAll();

    return markProductsInCart(username, allProducts);
  }

  @Override
  public List<Product> getProductsByCategory(String categoryName,String username) {
    List<Product> productsByCategory = productRepository.findByProductCategoryCategoryName(categoryName);

    return markProductsInCart(username, productsByCategory);
  }

  @Override
  public List<Product> searchProductsByNameOrDescription(String query,String username) {
    List<Product> filteredProducts = productRepository.findAll();

    //filter products by query
    List<Product> finalFilteredProducts = filteredProducts.stream()
            .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()) ||
                    p.getDescription().toLowerCase().contains(query.toLowerCase())).toList();


    return markProductsInCart(username, finalFilteredProducts);


  }

  private List<Product> markProductsInCart(String username, List<Product> products) {
    cartRepository.findByUserEmailAndCartStatus(username, CartStatus.ACTIVE).ifPresentOrElse(
            cart -> {
              //if cart is present, mark products in cart
              List<Product> productsInCart = cart.getProducts();
              products.forEach(p -> {
                if (productsInCart.contains(p)) {
                  p.setInCart(true);
                }
              });
            },
            //if cart is not present, mark all products as not in cart
            () -> products.forEach(p -> p.setInCart(false))
    );


    return products;
  }
}
