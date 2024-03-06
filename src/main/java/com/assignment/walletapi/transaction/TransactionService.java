package com.assignment.walletapi.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assignment.walletapi.transaction.models.Transaction;
import com.assignment.walletapi.transaction.models.TransactionType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Transaction createOne(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Page<Transaction> getTransactionsByUsername(String username, Pageable pageable) {
        return transactionRepository.findByUsername(username, pageable);
    }

    public Page<Transaction> getTransactionsByUsernameAndFilters(String username, TransactionType transactionType,
                                                                 BigDecimal minAmount, BigDecimal maxAmount,
                                                                 LocalDate startDate, LocalDate endDate,
                                                                 Pageable pageable) {
        return transactionRepository.findByUsernameAndFilters(username, transactionType, minAmount, maxAmount,
                startDate, endDate, pageable);
    }
    
}
