package com.example.user_service.domain.model;

import com.example.user_service.domain.exception.InvalidPasswordException;
import java.util.regex.Pattern;

public record Password(String value) {

  public Password {
    if (!isValid(value)) {
      throw new InvalidPasswordException("Password must be valid");
    }
  }

  private boolean isValid(String value) {
    if (value == null) {
      throw new InvalidPasswordException("Password must not be null");
    }
    if (value.isEmpty()) {
      throw new InvalidPasswordException("Password must not be empty");
    }
    if (value.length() < 8) {
      throw new InvalidPasswordException("Password length must be > 7");
    }
    if (value.length() > 32) {
      throw new InvalidPasswordException("Password length must be < 33");
    }

    String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,32}$";
    ;
    Pattern passwordPattern = Pattern.compile(passwordRegex);

    return passwordPattern.matcher(value).matches();
  }

}
