package com.example.table_service.exception;

public class TableNotFoundException extends RuntimeException {

  public TableNotFoundException(String message) {
    super(message);
  }

}
