package com.assignment.walletapi.customerprofile;

import org.springframework.stereotype.Service;

import com.assignment.walletapi.applicationuser.ApplicationUserService;
import com.assignment.walletapi.customerprofile.models.CustomerProfile;
import com.assignment.walletapi.customeruser.CustomerUserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;
    // private final ApplicationUserService applicationUserService;
    // private final CustomerUserService customerUserService;

    public CustomerProfile createOne(CustomerProfile customerProfile) {
        return customerProfileRepository.save(customerProfile);
    }

    // public CustomerProfile updateOne(CustomerProfile customerProfile) {
    //     CustomerProfile existingProfile = customerProfileRepository.findById(customerProfile.getId())
    //             .orElseThrow(() -> new ResourceNotFoundException("CustomerProfile not found"));
    
    //     // Update atribut name pada CustomerProfile
    //     existingProfile.setName(customerProfile.getName());
    
    //     // Simpan CustomerProfile yang diperbarui
    //     CustomerProfile updatedProfile = customerProfileRepository.save(existingProfile);
    
    //     // Ubah juga atribut name pada CustomerUser
    //     CustomerUser customerUser = existingProfile.getCustomerUser();
    //     if (customerUser != null) {
    //         customerUser.setName(customerProfile.getName());
    //         customerUserService.save(customerUser);
    //     }
    
    //     return updatedProfile;
    // }
    

    // public CustomerProfile getCustomerProfileByUser(UserPrincipal userPrincipal) {
    //     CustomerUser customerUser = userPrincipal.getCustomerUser();
    //     return customerUser.getCustomerProfile();
    // }

    // public CustomerProfile editCustomerProfile(CustomerProfileUpdateRequest requestDto) {
    //     UserPrincipal currentUser = getCurrentUser();
    //     CustomerProfile customerProfile = getCustomerProfile(currentUser);

    //     // Lakukan perubahan pada data customer profile
    //     customerProfile.setName(requestDto.getName());
    //     // Lakukan perubahan pada properti lainnya sesuai kebutuhan

    //     // Simpan perubahan pada customer profile ke dalam penyimpanan data
    //     return customerProfileRepository.save(customerProfile);
    // }

    // private UserPrincipal getCurrentUser() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     if (authentication == null || !authentication.isAuthenticated()) {
    //         throw new UnauthorizedAccessException();
    //     }

    //     return (UserPrincipal) authentication.getPrincipal();
    // }

    // private CustomerProfile getCustomerProfile(UserPrincipal user) {
    //     ApplicationUser applicationUser = applicationUserService.findByUsername(user.getUsername())
    //             .orElseThrow(() -> new ResourceNotFoundException());

    //     return applicationUser.getCustomerUser().getCustomerProfile();
    // }

    // public CustomerProfile updateOne(CustomerProfile customerProfile) {
    //     CustomerProfile existingCustomerProfile = this.findOneById(customerProfile.getUserId());
    //     customerProfile.setId(existingCustomerProfile.getId());

    //     if (customerProfile.getName() != null) {
    //         existingCustomerProfile.setName(customerProfile.getName());
    //     }
    //     if (customerProfile.getNik() != null) {
    //         existingCustomerProfile.setNik(customerProfile.getNik());
    //     }
    //     if (customerProfile.getDateOfBirth() != null) {
    //         existingCustomerProfile.setDateOfBirth(customerProfile.getDateOfBirth());
    //     }

    //     CustomerProfile updatedCustomerProfile = this.customerProfileRepository.save(existingCustomerProfile);
    //     return updatedCustomerProfile;
    // }
    
}
