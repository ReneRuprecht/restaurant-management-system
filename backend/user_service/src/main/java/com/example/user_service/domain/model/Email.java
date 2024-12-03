package com.example.user_service.domain.model;

import com.example.user_service.domain.exception.InvalidEmailException;
import java.util.regex.Pattern;

public record Email(String value) {

  public Email {
    if (!isValid(value)) {
      throw new InvalidEmailException("Email must be valid");
    }
  }

  private boolean isValid(String value) {
    if (value == null) {
      throw new InvalidEmailException("Email must not be null");
    }
    if (value.isEmpty()) {
      throw new InvalidEmailException("Email must not be empty");
    }
    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    Pattern emailPattern = Pattern.compile(emailRegex);

    return emailPattern.matcher(value).matches();
  }

}
