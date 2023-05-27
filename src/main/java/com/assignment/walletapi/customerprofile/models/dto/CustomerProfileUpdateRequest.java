package com.assignment.walletapi.customerprofile.models.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.assignment.walletapi.customerprofile.models.CustomerProfile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileUpdateRequest {

    private UUID id;

    private String name;

    private Integer nik;
    
    private LocalDate dateOfBirth;
    
    public CustomerProfile convertToEntity() {
        return CustomerProfile.builder()
                .id(this.id)
                .name(this.name)
                .nik(this.nik)
                .dateOfBirth(this.dateOfBirth)
                .build();
    }
}
