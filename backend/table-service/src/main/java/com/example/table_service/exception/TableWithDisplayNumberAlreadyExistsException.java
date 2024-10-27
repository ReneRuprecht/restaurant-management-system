package com.example.table_service.exception;

public class TableWithDisplayNumberAlreadyExistsException extends RuntimeException {

  public TableWithDisplayNumberAlreadyExistsException(String message) {
    super(message);
  }
}
