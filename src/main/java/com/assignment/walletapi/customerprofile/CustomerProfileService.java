package com.assignment.walletapi.customerprofile;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.assignment.walletapi.customerprofile.exception.CustomerProfileNotFoundException;
import com.assignment.walletapi.customerprofile.models.CustomerProfile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    public CustomerProfile createOne(CustomerProfile customerProfile) {
        return customerProfileRepository.save(customerProfile);
    }

    public CustomerProfile findOneById(UUID id) {
        return customerProfileRepository.findById(id).orElseThrow(() -> new CustomerProfileNotFoundException());
    }

    public CustomerProfile updateOne(CustomerProfile customerProfile) {
        CustomerProfile existingCustomerProfile = this.findOneById(customerProfile.getId());
        customerProfile.setId(existingCustomerProfile.getId());

        if (customerProfile.getName() != null) {
            existingCustomerProfile.setName(customerProfile.getName());
        }
        if (customerProfile.getNik() != null) {
            existingCustomerProfile.setNik(customerProfile.getNik());
        }
        if (customerProfile.getDateOfBirth() != null) {
            existingCustomerProfile.setDateOfBirth(customerProfile.getDateOfBirth());
        }

        CustomerProfile updatedCustomerProfile = this.customerProfileRepository.save(existingCustomerProfile);
        return updatedCustomerProfile;
    }
    
}
