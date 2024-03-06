package com.assignment.walletapi.wallet.models.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.assignment.walletapi.transaction.models.dto.TransactionResponse;

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
public class WalletResponse {

    private Long id;
    private UUID userId;
    private BigDecimal balance;
    private List<TransactionResponse> transactions;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
}
