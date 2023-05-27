package com.assignment.walletapi.customerprofile.models.dto;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileResponse {

    private UUID id;
    private String name;
    private Integer nik;
    private LocalDate dateOfBirth;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
}
