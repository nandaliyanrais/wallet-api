package com.assignment.walletapi.customerprofile.models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.assignment.walletapi.applicationuser.ApplicationUser;
import com.assignment.walletapi.customerprofile.models.dto.response.CustomerProfileResponse;
import com.assignment.walletapi.customerprofile.models.dto.response.CustomerProfileUpdateResponse;
import com.assignment.walletapi.customeruser.models.CustomerUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private Integer nik;

    private LocalDate dateOfBirth;

    @OneToOne
    // @JoinColumn(name = "customer_user_id")
    @Cascade(CascadeType.ALL)
    private CustomerUser customerUser;

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

    public CustomerProfileUpdateResponse convertToUpdateResponse() {
        return CustomerProfileUpdateResponse.builder()
                .id(this.id)
                .name(this.name)
                .nik(this.nik)
                .dateOfBirth(this.dateOfBirth)
                .updatedAt(this.updatedAt)
                .build();
    }
    
}
