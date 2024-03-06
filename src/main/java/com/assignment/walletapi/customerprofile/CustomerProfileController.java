package com.assignment.walletapi.customerprofile;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.walletapi.applicationuser.ApplicationUser;
import com.assignment.walletapi.applicationuser.ApplicationUserService;
import com.assignment.walletapi.authentication.models.UserPrincipal;
import com.assignment.walletapi.customerprofile.exception.ResourceNotFoundException;
import com.assignment.walletapi.customerprofile.exception.UnauthorizedAccessException;
import com.assignment.walletapi.customerprofile.models.CustomerProfile;
import com.assignment.walletapi.customerprofile.models.dto.request.CustomerProfileUpdateRequest;
import com.assignment.walletapi.customerprofile.models.dto.response.CustomerProfileResponse;
import com.assignment.walletapi.customerprofile.models.dto.response.CustomerProfileUpdateResponse;
import com.assignment.walletapi.customeruser.CustomerUserService;
import com.assignment.walletapi.customeruser.models.CustomerUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomerProfileController {

    private final CustomerProfileService customerProfileService;
    private final CustomerUserService customerUserService;

    // @PatchMapping("/customer-profile/{id}")
    // public ResponseEntity<CustomerProfileResponse> updateOne(@PathVariable("id") UUID userId, @RequestBody CustomerProfileUpdateRequest request) {
    //     CustomerProfile customerProfile = request.convertToEntity();
    //     customerProfile.setUserId(userId);
    //     CustomerProfile updatedCustomerProfile = this.customerProfileService.updateOne(customerProfile);

    //     return ResponseEntity.ok().body(updatedCustomerProfile.convertToResponse());
    // }

    // @PatchMapping("/customer-profile")
    // public ResponseEntity<CustomerProfileResponse> editCustomerProfile(
    //         @RequestBody CustomerProfileUpdateRequest requestDto
    // ) {
    //     CustomerProfile updatedCustomerProfile = customerProfileService.editCustomerProfile(requestDto);
    //     CustomerProfileResponse responseDto = updatedCustomerProfile.convertToResponse();
    //     return ResponseEntity.ok(responseDto);
    // }

    // @PatchMapping("/customer-profile")
    // public ResponseEntity<CustomerProfileResponse> updateCustomerProfile(
    //         @RequestBody CustomerProfileUpdateRequest request,
    //         @AuthenticationPrincipal UserPrincipal userPrincipal
    // ) {
    //     ApplicationUser applicationUser = applicationUserService.findByUsername(userPrincipal.getUsername())
    //             .orElseThrow(() -> new ResourceNotFoundException());

    //     CustomerUser customerUser = applicationUser.getCustomerUser();
    //     if (customerUser == null) {
    //         throw new ResourceNotFoundException();
    //     }

    //     CustomerProfile customerProfile = customerUser.getCustomerProfile();
    //     if (customerProfile == null) {
    //         throw new ResourceNotFoundException();
    //     }

    //     // Memeriksa apakah user memiliki customer profile
    //     // if (customerProfile == null) {
    //     //     throw new ResourceNotFoundException();
    //     // }

    //     // Memeriksa apakah customer profile yang dimiliki user sesuai dengan request
    //     if (!customerProfile.getId().equals(request.getId())) {
    //         throw new UnauthorizedAccessException();
    //     }

    //     // Memperbarui data pada customerProfile dengan data dari request
    //     customerProfile.setName(request.getName());
    //     customerProfile.setNik(request.getNik());
    //     customerProfile.setDateOfBirth(request.getDateOfBirth());

    //     // Menyimpan perubahan pada customerProfile
    //     customerProfile = customerProfileService.createOne(customerProfile);

    //     // Mengkonversi customerProfile menjadi CustomerProfileResponse
    //     CustomerProfileResponse response = customerProfile.convertToResponse();

    //     return ResponseEntity.ok(response);
    // }

    @PatchMapping("/customer/profile")
    public ResponseEntity<CustomerProfileUpdateResponse> updateCustomerProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody CustomerProfileUpdateRequest request) {

        Optional<CustomerUser> customerUser = customerUserService.findCustomerUserByUsername(currentUser.getUsername());

        if (customerUser.isPresent()) {
            CustomerUser existingCustomerUser = customerUser.get();
            CustomerProfile customerProfile = existingCustomerUser.getCustomerProfile();
    
            if (customerProfile != null) {
                // Proses pembaruan atribut yang diperlukan
                if (request.getName() != null) {
                    customerProfile.setName(request.getName());

                    existingCustomerUser.setName(request.getName());
                    customerUserService.save(existingCustomerUser);
                }
                if (request.getNik() != null) {
                    customerProfile.setNik(request.getNik());
                }
                if (request.getDateOfBirth() != null) {
                    customerProfile.setDateOfBirth(request.getDateOfBirth());
                }
    
                // Menyimpan CustomerProfile yang diperbarui
                customerProfile.setId(customerProfile.getId());
                CustomerProfile updatedCustomerProfile = customerProfileService.createOne(customerProfile);
    
                // Mengonversi hasil pembaruan menjadi respons
                CustomerProfileUpdateResponse response = updatedCustomerProfile.convertToUpdateResponse();
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/customer/profile")
    public ResponseEntity<CustomerProfileResponse> getCustomerProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        Optional<CustomerUser> customerUser = customerUserService.findCustomerUserByUsername(currentUser.getUsername());

        if (customerUser.isPresent()) {
            CustomerUser existingCustomerUser = customerUser.get();
            CustomerProfile customerProfile = existingCustomerUser.getCustomerProfile();

            if (customerProfile != null) {
                CustomerProfileResponse response = customerProfile.convertToResponse();
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.notFound().build();
    }
    
}
