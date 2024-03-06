package com.assignment.walletapi.customeruser.models;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.assignment.walletapi.applicationuser.ApplicationUser;
import com.assignment.walletapi.customerprofile.models.CustomerProfile;
import com.assignment.walletapi.customeruser.models.dto.CustomerUserResponse;
import com.assignment.walletapi.wallet.models.Wallet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class CustomerUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    @OneToOne
    private CustomerProfile customerProfile;

    @OneToOne
    private Wallet wallet;

    @OneToOne
    @Cascade(CascadeType.ALL)
    private ApplicationUser applicationUser;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public CustomerUserResponse convertToResponse() {
        UUID userId = this.applicationUser.getId();
        return CustomerUserResponse.builder()
                .userId(userId)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .build();
    }
}