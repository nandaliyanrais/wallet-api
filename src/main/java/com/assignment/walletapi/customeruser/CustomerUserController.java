package com.assignment.walletapi.customeruser;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.walletapi.authentication.models.UserPrincipal;
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

    @GetMapping("/customers/{username}")
    public ResponseEntity<CustomerUserResponse> getCustomerUser(@PathVariable("username") String username,
                                                                @AuthenticationPrincipal UserPrincipal currentUser) {
        if (!currentUser.getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<CustomerUser> customerUser = customerUserService.findCustomerUserByUsername(username);

        if (customerUser.isPresent()) {
            CustomerUserResponse response = customerUser.get().convertToResponse();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<CustomerUserResponse> getCustomerUser(@AuthenticationPrincipal UserPrincipal currentUser) {
        Optional<CustomerUser> customerUser = customerUserService.findCustomerUserByUsername(currentUser.getUsername());

        if (customerUser.isPresent()) {
            CustomerUserResponse response = customerUser.get().convertToResponse();
            return ResponseEntity.ok(response);
        } 

        return ResponseEntity.notFound().build();
    }
    
    
}
