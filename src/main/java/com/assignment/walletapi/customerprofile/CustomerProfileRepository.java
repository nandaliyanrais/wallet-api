package com.assignment.walletapi.customerprofile;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.walletapi.customerprofile.models.CustomerProfile;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, UUID> {
    
}
