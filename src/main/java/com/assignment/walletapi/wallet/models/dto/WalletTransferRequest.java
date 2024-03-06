package com.assignment.walletapi.wallet.models.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransferRequest {

    private String receiverWallet;
    private BigDecimal amount;
    
}
