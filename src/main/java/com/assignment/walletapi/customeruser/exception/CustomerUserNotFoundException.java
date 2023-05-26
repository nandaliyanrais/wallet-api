package com.assignment.walletapi.customeruser.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class CustomerUserNotFoundException extends RuntimeException {

    public CustomerUserNotFoundException() {
        
    }

    public CustomerUserNotFoundException(String message) {
        super(message);
    }
}
