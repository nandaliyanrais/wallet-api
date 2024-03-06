package com.assignment.walletapi.customerprofile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User is not authenticated")
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException() {
        
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }

}
