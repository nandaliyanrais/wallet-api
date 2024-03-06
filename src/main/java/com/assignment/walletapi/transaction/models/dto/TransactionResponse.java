package com.assignment.walletapi.transaction.models.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.assignment.walletapi.transaction.models.TransactionType;

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
public class TransactionResponse {

    private Long id;
    private TransactionType transactionType;
    private String description;
    private BigDecimal amount;
    private Timestamp createdAt;

    
}
