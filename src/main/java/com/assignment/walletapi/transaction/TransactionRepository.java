package com.assignment.walletapi.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.assignment.walletapi.transaction.models.Transaction;
import com.assignment.walletapi.transaction.models.TransactionType;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByUsername(String username, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.username = :username " +
           "AND (:transactionType IS NULL OR t.transactionType = :transactionType) " +
           "AND (:minAmount IS NULL OR t.amount >= :minAmount) " +
           "AND (:maxAmount IS NULL OR t.amount <= :maxAmount) " +
           "AND (:startDate IS NULL OR t.createdAt >= :startDate) " +
           "AND (:endDate IS NULL OR t.createdAt <= :endDate) " +
           "ORDER BY t.createdAt DESC")
    Page<Transaction> findByUsernameAndFilters(
            @Param("username") String username,
            @Param("transactionType") TransactionType transactionType,
            @Param("minAmount") BigDecimal minAmount,
            @Param("maxAmount") BigDecimal maxAmount,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
    
}
