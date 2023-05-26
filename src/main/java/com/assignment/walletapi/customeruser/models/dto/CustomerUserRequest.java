package com.assignment.walletapi.customeruser.models.dto;

import java.util.UUID;

import com.assignment.walletapi.customeruser.models.CustomerUser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUserRequest {

    private UUID id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    private String password;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    public CustomerUser convertToEntity() {
        return CustomerUser.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .build();
    }
    
}
