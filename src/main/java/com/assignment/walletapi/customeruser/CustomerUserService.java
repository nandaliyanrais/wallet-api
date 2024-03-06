package com.assignment.walletapi.customeruser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.walletapi.applicationuser.ApplicationUser;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<CustomerUser> findAll() {
        return customerUserRepository.findAll();
    }

    public CustomerUser findOneById(Long id) {
        return customerUserRepository.findById(id).orElseThrow(() -> new CustomerUserNotFoundException());
    }

    public CustomerUser save(CustomerUser customerUser) {
        return customerUserRepository.save(customerUser);
    }

    public CustomerUser createOne(CustomerUser customerUser) {
        // if (customerUserRepository.existsByUsername(customerUser.getUsername())) {
        //     throw new IllegalArgumentException("Username already exists");
        // }

        // if (customerUserRepository.existsByEmail(customerUser.getEmail())) {
        //     throw new IllegalArgumentException("Email already exists");
        // }

        // UUID userId = UUID.randomUUID();
        // String name = customerUser.getName();

        // CustomerProfile customerProfile = CustomerProfile.builder()
        //         .customerUser(customerUser)
        //         .name(name)
        //         .build();

        // Wallet wallet = Wallet.builder()
        //         .customerUser(customerUser)
        //         .balance(new BigDecimal("100000"))
        //         .build();

        // // userProfile.setWallet(wallet);
        // // wallet.setUserProfile(userProfile);
        // customerUser.setUserId(userId);

        // customerProfileService.createOne(customerProfile);
        // walletService.createOne(wallet);

        ApplicationUser applicationUser = customerUser.getApplicationUser();
        String hashPassword = bCryptPasswordEncoder.encode(applicationUser.getPassword());
        applicationUser.setPassword(hashPassword);

        Wallet wallet = new Wallet();
        wallet.setBalance(new BigDecimal("100000")); 
        wallet.setCustomerUser(customerUser);// Set saldo awal ke 0
        Wallet savedWallet = walletService.createOne(wallet); // Menyimpan Wallet ke dalam penyimpanan data
        customerUser.setWallet(savedWallet); // Mengatur Wallet pada CustomerUser

        // Membuat objek CustomerProfile baru untuk CustomerUser
        CustomerProfile customerProfile = new CustomerProfile();
        customerProfile.setName(customerUser.getName());
        customerProfile.setCustomerUser(customerUser);
        // Set properti lainnya sesuai kebutuhan
        CustomerProfile savedCustomerProfile = customerProfileService.createOne(customerProfile); // Menyimpan CustomerProfile ke dalam penyimpanan data
        customerUser.setCustomerProfile(savedCustomerProfile); // Mengatur CustomerProfile pada CustomerUser

        return customerUserRepository.save(customerUser);
    }

    public Optional<CustomerUser> findCustomerUserByUsername(String username) {
        return customerUserRepository.findByUsername(username);
    }

    // public CustomerUser getCurrentUser() {
    //     String username = SecurityContextHolder.getContext().getAuthentication().getName();
    //     ApplicationUser applicationUser = applicationUserRepository.findByUsername(username)
    //             .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    //     return customerUserRepository.findByApplicationUser(applicationUser)
    //             .orElseThrow(() -> new CustomerUserNotFoundException());
    // }
    
}
