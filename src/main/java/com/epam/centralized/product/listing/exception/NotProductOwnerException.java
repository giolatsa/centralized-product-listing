package com.epam.centralized.product.listing.exception;

public class NotProductOwnerException extends RuntimeException {

  public NotProductOwnerException(String message) {
    super(message);
  }
}
