package com.example.user_service.domain.exception;

public class InvalidPasswordException extends  RuntimeException{
  public InvalidPasswordException(String message){
    super(message);
  }

}
