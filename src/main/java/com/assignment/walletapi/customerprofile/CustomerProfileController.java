package com.assignment.walletapi.customerprofile;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.walletapi.customerprofile.models.CustomerProfile;
import com.assignment.walletapi.customerprofile.models.dto.CustomerProfileResponse;
import com.assignment.walletapi.customerprofile.models.dto.CustomerProfileUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;

    @PatchMapping("/customer-profile/{id}")
    public ResponseEntity<CustomerProfileResponse> updateOne(@PathVariable("id") UUID id, @RequestBody CustomerProfileUpdateRequest request) {
        CustomerProfile customerProfile = request.convertToEntity();
        customerProfile.setId(id);
        CustomerProfile updatedCustomerProfile = this.customerProfileService.updateOne(customerProfile);

        return ResponseEntity.ok().body(updatedCustomerProfile.convertToResponse());
    }

    
    
}
