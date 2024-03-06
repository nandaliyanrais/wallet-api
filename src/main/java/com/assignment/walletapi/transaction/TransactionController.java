package com.assignment.walletapi.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.walletapi.authentication.models.UserPrincipal;
import com.assignment.walletapi.transaction.models.Transaction;
import com.assignment.walletapi.transaction.models.TransactionType;
import com.assignment.walletapi.transaction.models.dto.TransactionResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // @GetMapping("/transactions")
    // public ResponseEntity<Page<TransactionResponse>> getTransactionsByUser(
    //         @AuthenticationPrincipal UserPrincipal currentUser,
    //         @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

    //     // Mendapatkan username pengguna yang terautentikasi
    //     String username = currentUser.getUsername();

    //     // Mendapatkan daftar transaksi pengguna dengan paginasi dan pengurutan default
    //     Page<Transaction> transactionPage = transactionService.getTransactionsByUsername(username, pageable);

    //     // Mengonversi halaman transaksi ke dalam format respons
    //     Page<TransactionResponse> transactionResponsePage = transactionPage.map(Transaction::convertToResponse);

    //     return ResponseEntity.ok(transactionResponsePage);
    // }

    // @GetMapping("/transactions")
    // public ResponseEntity<Page<TransactionResponse>> getTransactionsByUser(
    //     @AuthenticationPrincipal UserPrincipal currentUser,
    //     @RequestParam(required = false) TransactionType transactionType,
    //     @RequestParam(required = false) BigDecimal minAmount,
    //     @RequestParam(required = false) BigDecimal maxAmount,
    //     @RequestParam(required = false) LocalDate startDate,
    //     @RequestParam(required = false) LocalDate endDate,
    //     @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

    //     // Mendapatkan username pengguna yang terautentikasi
    //     String username = currentUser.getUsername();

    //     // Mendapatkan daftar transaksi pengguna dengan filter dan paginasi
    //     Page<Transaction> transactionPage = transactionService.getTransactionsByUsernameAndFilters(username,
    //             transactionType, minAmount, maxAmount, startDate, endDate, pageable);

    //     // Mengonversi halaman transaksi ke dalam format respons
    //     Page<TransactionResponse> transactionResponsePage = transactionPage.map(Transaction::convertToResponse);

    //     return ResponseEntity.ok(transactionResponsePage);
    // }

    @GetMapping("/transactions")
    public ResponseEntity<Page<TransactionResponse>> getTransactionsByUser(
        @AuthenticationPrincipal UserPrincipal currentUser,
        @RequestParam(required = false) TransactionType transactionType,
        @RequestParam(required = false) BigDecimal minAmount,
        @RequestParam(required = false) BigDecimal maxAmount,
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate,
        @RequestParam(required = false) String sortField,
        @RequestParam(required = false) String sortDirection,
        @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        // Mendapatkan username pengguna yang terautentikasi
        String username = currentUser.getUsername();

        // Menentukan pengurutan berdasarkan pengaturan pengguna
        Sort sort = pageable.getSort();
        if (sortField != null && sortDirection != null) {
            sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        }

        // Mendapatkan daftar transaksi pengguna dengan filter, pengurutan, dan paginasi
        Pageable modifiedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Page<Transaction> transactionPage = transactionService.getTransactionsByUsernameAndFilters(username,
                transactionType, minAmount, maxAmount, startDate, endDate, modifiedPageable);

        // Mengonversi halaman transaksi ke dalam format respons
        Page<TransactionResponse> transactionResponsePage = transactionPage.map(Transaction::convertToResponse);

        return ResponseEntity.ok(transactionResponsePage);
    }

    // private Sort.Order parseSortOrder(String sort) {
    //     String[] parts = sort.split(",");
    //     if (parts.length == 2) {
    //         String property = parts[0].trim();
    //         String direction = parts[1].trim();
    //         if ("transactionType".equalsIgnoreCase(property)) {
    //             return new Sort.Order(Sort.Direction.fromString(direction), "transactionType");
    //         } else if ("amount".equalsIgnoreCase(property)) {
    //             return new Sort.Order(Sort.Direction.fromString(direction), "amount");
    //         } else if ("transactionDate".equalsIgnoreCase(property)) {
    //             return new Sort.Order(Sort.Direction.fromString(direction), "createdAt");
    //         }
    //     }
    //     // Default sort by createdAt in descending order
    //     return new Sort.Order(Sort.Direction.DESC, "createdAt");
    // }


    
}
