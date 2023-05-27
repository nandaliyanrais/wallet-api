package com.assignment.walletapi.customeruser.models;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.assignment.walletapi.customeruser.models.dto.CustomerUserResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    // @GeneratedValue(generator = "UUID")
    // @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    // @OneToOne
    // @Cascade(CascadeType.ALL)
    // private ApplicationUser applicationUser;

    // @OneToOne
    // @Cascade(CascadeType.ALL)
    // private CustomerProfile customerProfile;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public CustomerUserResponse convertToResponse() {
        return CustomerUserResponse.builder()
                .id(this.id)
                .name(this.name)
                .username(this.username)
                .email(this.email)
                .build();
    }
}
