package com.assignment.walletapi.customeruser.models.dto;

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
public class CustomerUserResponse {

    private UUID id;
    private String name;
    private String username;
    private String email;
    
}
