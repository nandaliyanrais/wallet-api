package com.assignment.walletapi.customerprofile.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.assignment.walletapi.customerprofile.models.dto.CustomerProfileResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfile {

    @Id
    private UUID id;

    private String name;

    @Column(unique = true)
    private Integer nik;

    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public CustomerProfileResponse convertToResponse() {
        return CustomerProfileResponse.builder()
                .id(this.id)
                .name(this.name)
                .nik(this.nik)
                .dateOfBirth(this.dateOfBirth)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
    
}
