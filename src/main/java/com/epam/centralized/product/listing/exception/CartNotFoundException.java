package com.epam.centralized.product.listing.exception;

import jakarta.persistence.EntityNotFoundException;

public class CartNotFoundException extends EntityNotFoundException {

  public CartNotFoundException(String message) {
    super(message);
  }
}
