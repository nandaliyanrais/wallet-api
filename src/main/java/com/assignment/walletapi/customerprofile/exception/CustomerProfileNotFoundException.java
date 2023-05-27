package com.assignment.walletapi.customerprofile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Customer profile not found")
public class CustomerProfileNotFoundException extends RuntimeException {

    public CustomerProfileNotFoundException() {
        
    }

    public CustomerProfileNotFoundException(String message) {
        super(message);
    }

}
