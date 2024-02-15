package com.epam.centralized.product.listing.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
