package com.assignment.walletapi.transaction.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;

import com.assignment.walletapi.customeruser.models.CustomerUser;
import com.assignment.walletapi.transaction.models.dto.TransactionResponse;
import com.assignment.walletapi.wallet.models.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    private String description;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @Cascade(CascadeType.ALL)
    @JsonIgnore
    private Wallet wallet;

    private BigDecimal amount;

    @CreationTimestamp
    private Timestamp createdAt;

    public TransactionResponse convertToResponse() {
        return TransactionResponse.builder()
                .id(this.id)
                .transactionType(this.transactionType)
                .description(this.description)
                .amount(this.amount)
                .createdAt(this.createdAt)
                .build();
    }
    
}
