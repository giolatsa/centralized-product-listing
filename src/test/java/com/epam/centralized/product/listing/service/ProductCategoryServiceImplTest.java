package com.epam.centralized.product.listing.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.epam.centralized.product.listing.model.ProductCategory;
import com.epam.centralized.product.listing.repository.ProductCategoryRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ProductCategoryServiceImplTest {

  @Mock private ProductCategoryRepository productCategoryRepository;

  @InjectMocks private ProductCategoryServiceImpl productCategoryService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllCategories_ReturnsAllCategories() {
    // Arrange
    List<ProductCategory> expectedCategories = List.of(new ProductCategory());
    when(productCategoryRepository.findAll()).thenReturn(expectedCategories);

    // Act
    List<ProductCategory> result = productCategoryService.getAllCategories();

    // Assert
    assertNotNull(result, "Result should not be null");
    assertFalse(result.isEmpty(), "Result list should not be empty");
    assertEquals(
        expectedCategories.size(),
        result.size(),
        "The size of the results should match the expected");
    verify(productCategoryRepository, times(1)).findAll();
  }
}
