package com.example.user_service.domain.model;

import com.example.user_service.domain.exception.InvalidEmailException;

public record Username(String value) {

  public Username {
    if (!isValid(value)) {
      throw new InvalidEmailException("Username must be valid");
    }
  }

  private boolean isValid(String value) {
    return !value.isEmpty();
  }

}
