package com.assignment.walletapi.wallet;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.walletapi.wallet.models.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    
}
