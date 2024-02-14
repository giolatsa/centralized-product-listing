package com.epam.centralized.product.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.centralized.product.listing.model.*;
import com.epam.centralized.product.listing.model.enums.CartStatus;
import com.epam.centralized.product.listing.model.enums.ProductStatus;
import com.epam.centralized.product.listing.repository.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductServiceImplTest {

  @Mock private ProductRepository productRepository;

  @Mock private CartRepository cartRepository;

  @Mock private ProductCategoryRepository productCategoryRepository;

  @Mock private UserRepository userRepository;

  @Mock private CompanyRepository companyRepository;

  @InjectMocks private ProductServiceImpl productService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllProducts_ReturnsAllActiveProducts() {
    // Arrange
    String username = "test@example.com";
    Product product1 = new Product();
    product1.setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0));
    Product product2 = new Product();
    product2.setCreateDate(LocalDateTime.of(2024, 1, 2, 0, 0));

    List<Product> products = List.of(product1, product2);
    when(productRepository.findAllByProductStatus(ProductStatus.ACTIVE)).thenReturn(products);

    // Act
    List<Product> result = productService.getAllProducts(username);

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(productRepository, times(1)).findAllByProductStatus(ProductStatus.ACTIVE);
    assertEquals(2, result.size());
    // make sure it got sorted by date desc
    assertEquals(product2, result.get(0));
  }

  @Test
  void createProduct_ValidUserAndCompany_ProductCreated() {
    // Arrange
    String username = "user@example.com";
    User user = new User();
    Company company = new Company();
    Product product = new Product();
    when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
    when(companyRepository.findByUser(user)).thenReturn(Optional.of(company));
    when(productRepository.save(any(Product.class))).thenReturn(product);

    // Act
    Product createdProduct = productService.createProduct(product, username);

    // Assert
    assertNotNull(createdProduct);
    verify(userRepository, times(1)).findByEmail(username);
    verify(companyRepository, times(1)).findByUser(user);
    verify(productRepository, times(1)).save(product);
  }

  @Test
  void deleteProduct_OwnerDeletingProduct_ProductDeleted() {
    // Arrange
    String username = "owner@example.com";
    Long productId = 1L;
    User user = new User(); // Set necessary fields
    Company company = new Company(); // Set necessary fields
    company.setUser(user);
    Product product = new Product(); // Set necessary fields
    product.setCompany(company);
    when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // Act
    productService.deleteProduct(productId, username);

    // Assert
    verify(userRepository, times(1)).findByEmail(username);
    verify(productRepository, times(1)).findById(productId);
    verify(productRepository, times(1)).save(product);
    assertEquals(ProductStatus.DISABLED, product.getProductStatus());
  }

  @Test
  void getProductsByCategory_ValidCategory_ReturnsFilteredProducts() {
    // Arrange
    String categoryName = "Electronics";
    String username = "user@example.com";
    List<Product> products = List.of(new Product());
    when(productRepository.findByProductCategoryCategoryNameAndProductStatus(
            eq(categoryName), eq(ProductStatus.ACTIVE)))
        .thenReturn(products);

    // Act
    List<Product> result = productService.getProductsByCategory(categoryName, username);

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(productRepository, times(1))
        .findByProductCategoryCategoryNameAndProductStatus(categoryName, ProductStatus.ACTIVE);
  }

  @Test
  void searchProductsByNameOrDescription_QueryMatches_ReturnsFilteredProducts() {
    // Arrange
    String query = "test";
    String username = "user@example.com";
    List<Product> allProducts = List.of(new Product(), new Product());
    // Assuming first product matches query, second does not
    allProducts.get(0).setName("test product");
    allProducts.get(0).setDescription("related");
    allProducts.get(0).setCreateDate(LocalDateTime.now());
    allProducts.get(1).setName("unrelated product");
    allProducts.get(1).setDescription("unrelated");
    allProducts.get(1).setCreateDate(LocalDateTime.now());
    when(productRepository.findAll()).thenReturn(allProducts);

    // Act
    List<Product> result = productService.searchProductsByNameOrDescription(query, username);

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    verify(productRepository, times(1)).findAll();
  }

  @Test
  void getProductsInCart_UserHasActiveCart_ReturnsProductsInCart() {
    // Arrange
    String username = "user@example.com";
    Cart cart = new Cart();
    cart.setProducts(List.of(new Product()));
    when(cartRepository.findByUserEmailAndCartStatus(username, CartStatus.ACTIVE))
        .thenReturn(Optional.of(cart));

    // Act
    List<Product> result = productService.getProductsInCart(username);

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(cartRepository, times(1)).findByUserEmailAndCartStatus(username, CartStatus.ACTIVE);
  }

  @Test
  void findAllProductsByCompany_ValidCompany_ReturnsCompanyProducts() {
    // Arrange
    Company company = new Company();
    List<Product> products = List.of(new Product());
    when(productRepository.findAllByCompany(company)).thenReturn(products);

    // Act
    List<Product> result = productService.findAllProductsByCompany(company);

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(productRepository, times(1)).findAllByCompany(company);
  }

  @Test
  void findAllProductCategories_ReturnsAllCategories() {
    // Arrange
    List<ProductCategory> categories = List.of(new ProductCategory());
    when(productCategoryRepository.findAll()).thenReturn(categories);

    // Act
    List<ProductCategory> result = productService.findAllProductCategories();

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(productCategoryRepository, times(1)).findAll();
  }

  @Test
  void getAllProducts_markProductsInCart_ReturnsAllActiveProducts() {
    // Arrange
    String username = "user@example.com";
    Product product1 = new Product();
    product1.setCreateDate(LocalDateTime.of(2021, 1, 1, 0, 0));
    product1.setName("test product 1");
    Product product2 = new Product();
    product2.setCreateDate(LocalDateTime.of(2024, 1, 3, 0, 0));
    product2.setName("test product 2");
    Product product3 = new Product();
    product3.setName("test product 3");
    product3.setCreateDate(LocalDateTime.of(2024, 1, 2, 0, 0));

    List<Product> products = List.of(product1, product2, product3);
    when(productRepository.findAllByProductStatus(ProductStatus.ACTIVE)).thenReturn(products);
    Cart cart = new Cart();
    cart.setProducts(List.of(product2));
    when(cartRepository.findByUserEmailAndCartStatus(username, CartStatus.ACTIVE))
        .thenReturn(Optional.of(cart));

    // Act
    List<Product> result = productService.getAllProducts(username);

    // Assert
    assertNotNull(result);
    assertFalse(result.isEmpty());
    verify(productRepository, times(1)).findAllByProductStatus(ProductStatus.ACTIVE);
    assertEquals(3, result.size());
    // make sure it got sorted by date desc
    assertEquals(product2, result.get(0));
    assertEquals(product3, result.get(1));
    assertEquals(product1, result.get(2));

    // make sure product in cart is marked
    assertTrue(result.get(0).getInCart());
    assertFalse(result.get(1).getInCart());
    assertFalse(result.get(2).getInCart());
  }
}
