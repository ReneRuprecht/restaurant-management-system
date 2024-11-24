package com.example.table_service.domain.exception;

public class TableNotFoundException extends RuntimeException {

  public TableNotFoundException(String message) {
    super(message);
  }

}
