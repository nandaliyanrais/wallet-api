package com.assignment.walletapi.customeruser;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.walletapi.customeruser.exception.CustomerUserNotFoundException;
import com.assignment.walletapi.customeruser.models.CustomerUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserService {

    private final CustomerUserRepository userRepository;

    public List<CustomerUser> findAll() {
        return userRepository.findAll();
    }

    public CustomerUser findOneById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomerUserNotFoundException());
    }

    public CustomerUser createOne(CustomerUser user) {
        return userRepository.save(user);
    }
    
}
