package com.example.user_service.domain.exception;

public class InvalidEmailException extends RuntimeException {

  public InvalidEmailException(String message) {
    super(message);
  }

}