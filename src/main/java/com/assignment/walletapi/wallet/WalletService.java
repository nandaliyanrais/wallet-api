package com.assignment.walletapi.wallet;

import org.springframework.stereotype.Service;

import com.assignment.walletapi.wallet.models.Wallet;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    public Wallet createOne(Wallet wallet) {
        return walletRepository.save(wallet);
    }
    
}
