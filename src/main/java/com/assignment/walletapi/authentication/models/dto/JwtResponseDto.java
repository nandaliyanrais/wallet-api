package com.assignment.walletapi.authentication.models.dto;

import lombok.Data;

@Data
public class JwtResponseDto {
    
    private String token;
    private String type = "Bearer";

    public JwtResponseDto(String token) {
        this.token = token;
    }
    
}
