package com.assignment.walletapi.customeruser;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.assignment.walletapi.customerprofile.CustomerProfileService;
import com.assignment.walletapi.customerprofile.models.CustomerProfile;
import com.assignment.walletapi.customeruser.exception.CustomerUserNotFoundException;
import com.assignment.walletapi.customeruser.models.CustomerUser;
import com.assignment.walletapi.wallet.WalletService;
import com.assignment.walletapi.wallet.models.Wallet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerUserService {

    private final CustomerUserRepository customerUserRepository;
    private final CustomerProfileService customerProfileService;
    private final WalletService walletService;

    public List<CustomerUser> findAll() {
        return customerUserRepository.findAll();
    }

    public CustomerUser findOneById(Long id) {
        return customerUserRepository.findById(id).orElseThrow(() -> new CustomerUserNotFoundException());
    }

    public CustomerUser createOne(CustomerUser customerUser) {
        if (customerUserRepository.existsByUsername(customerUser.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (customerUserRepository.existsByEmail(customerUser.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UUID userId = UUID.randomUUID();
        String name = customerUser.getName();

        CustomerProfile userProfile = CustomerProfile.builder()
                .id(userId)
                .name(name)
                .build();

        Wallet wallet = Wallet.builder()
                .id(userId)
                .balance(new BigDecimal("100000"))
                .build();

        // userProfile.setWallet(wallet);
        // wallet.setUserProfile(userProfile);
        customerUser.setId(userId);

        customerProfileService.createOne(userProfile);
        walletService.createOne(wallet);

        return customerUserRepository.save(customerUser);
    }
    
}
