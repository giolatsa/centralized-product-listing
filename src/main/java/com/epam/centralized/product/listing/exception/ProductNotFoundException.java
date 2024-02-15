package com.epam.centralized.product.listing.exception;

import jakarta.persistence.EntityNotFoundException;

public class ProductNotFoundException extends EntityNotFoundException {

  public ProductNotFoundException(String message) {
    super(message);
  }
}
