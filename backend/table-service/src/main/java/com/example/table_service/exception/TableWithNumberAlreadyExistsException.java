package com.example.table_service.exception;

public class TableWithNumberAlreadyExistsException extends RuntimeException {

  public TableWithNumberAlreadyExistsException(String message) {
    super(message);
  }
}
