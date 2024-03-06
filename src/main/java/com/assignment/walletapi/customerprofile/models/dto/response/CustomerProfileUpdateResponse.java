package com.assignment.walletapi.customerprofile.models.dto.response;

import java.sql.Timestamp;
import java.time.LocalDate;

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
public class CustomerProfileUpdateResponse {
    
    private Long id;
    private String name;
    private Integer nik;
    private LocalDate dateOfBirth;
    private Timestamp updatedAt;

}
