package com.assignment.walletapi.customeruser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.walletapi.customeruser.models.CustomerUser;
import com.assignment.walletapi.customeruser.models.dto.CustomerUserRequest;
import com.assignment.walletapi.customeruser.models.dto.CustomerUserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerUserController {

    private final CustomerUserService customerUserService;

    @PostMapping("/register")
    public ResponseEntity<CustomerUserResponse> createOne(@Valid @RequestBody CustomerUserRequest customerUserRequest) {
        CustomerUser newCustomerUser = customerUserRequest.convertToEntity();
        CustomerUser saveCustomerUser = this.customerUserService.createOne(newCustomerUser);
        CustomerUserResponse customerUserResponse = saveCustomerUser.convertToResponse();

        return ResponseEntity.status(HttpStatus.CREATED).body(customerUserResponse);
    }


    
    
}
