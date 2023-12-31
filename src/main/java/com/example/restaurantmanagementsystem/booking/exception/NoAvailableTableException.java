package com.example.restaurantmanagementsystem.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoAvailableTableException extends  RuntimeException{

    public NoAvailableTableException(String message){
        super(message);
    }
}
