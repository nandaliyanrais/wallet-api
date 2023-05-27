package com.assignment.walletapi.customeruser;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.walletapi.customeruser.models.CustomerUser;

public interface CustomerUserRepository extends JpaRepository<CustomerUser, Long> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
}
