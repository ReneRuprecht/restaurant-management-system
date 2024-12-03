package com.example.user_service.domain.model;

import com.example.user_service.domain.exception.InvalidEmailException;

public record UserID(Long value) {

  public UserID {
    if (!isValid(value)) {
      throw new InvalidEmailException("Email must be valid");
    }
  }

  private boolean isValid(Long value) {
    return value > 0;
  }

}
