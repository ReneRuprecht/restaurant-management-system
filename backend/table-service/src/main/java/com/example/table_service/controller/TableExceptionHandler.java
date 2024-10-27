package com.example.table_service.controller;

import com.example.table_service.exception.TableNotFoundException;
import com.example.table_service.exception.TableWithDisplayNumberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TableExceptionHandler {

  @ExceptionHandler(TableNotFoundException.class)
  public ResponseEntity<String> handleTableNotFoundException(TableNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(TableWithDisplayNumberAlreadyExistsException.class)
  public ResponseEntity<String> handleTableWithDisplayNumberAlreadyExistsException(
      TableWithDisplayNumberAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}
