package com.assignment.walletapi.wallet;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.walletapi.authentication.models.UserPrincipal;
import com.assignment.walletapi.customeruser.CustomerUserService;
import com.assignment.walletapi.customeruser.models.CustomerUser;
import com.assignment.walletapi.transaction.TransactionService;
import com.assignment.walletapi.transaction.models.Transaction;
import com.assignment.walletapi.transaction.models.TransactionType;
import com.assignment.walletapi.transaction.models.dto.TransactionResponse;
import com.assignment.walletapi.wallet.models.Wallet;
import com.assignment.walletapi.wallet.models.dto.WalletResponse;
import com.assignment.walletapi.wallet.models.dto.WalletTopUpRequest;
import com.assignment.walletapi.wallet.models.dto.WalletTransferRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final CustomerUserService customerUserService;
    private final TransactionService transactionService;

    @GetMapping("/wallet")
    public ResponseEntity<WalletResponse> getWallet(@AuthenticationPrincipal UserPrincipal currentUser) {
        Optional<CustomerUser> customerUser = customerUserService.findCustomerUserByUsername(currentUser.getUsername());

        if (customerUser.isPresent()) {
            CustomerUser existingCustomerUser = customerUser.get();
            Wallet wallet = existingCustomerUser.getWallet();

            if (wallet != null) {
                WalletResponse response = wallet.convertToResponse();
                return ResponseEntity.ok(response);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/wallet/topup")
    public ResponseEntity<TransactionResponse> topUpWallet(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody WalletTopUpRequest request) {

        Optional<CustomerUser> customerUserOptional = customerUserService.findCustomerUserByUsername(currentUser.getUsername());

        if (customerUserOptional.isPresent()) {
            CustomerUser customerUser = customerUserOptional.get();
            Wallet wallet = customerUser.getWallet();

            BigDecimal topUpAmount = request.getAmount();
            wallet.increaseBalance(topUpAmount);

            String description = request.getDescription();
            if (description == null || description.isEmpty()) {
                description = "Top Up";
            }

            Transaction transaction = Transaction.builder()
                    .username(currentUser.getUsername())
                    .transactionType(TransactionType.IN)
                    .description(description)
                    .amount(topUpAmount)
                    .wallet(wallet)
                    .build();

            Transaction savedTransaction = transactionService.createOne(transaction);
            TransactionResponse response = savedTransaction.convertToResponse();
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/wallet/transfer")
    public ResponseEntity<TransactionResponse> transferBetweenWallets(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestBody WalletTransferRequest request) {

        String sourceUsername = currentUser.getUsername();
        String destinationUsername = request.getReceiverWallet();
        BigDecimal transferAmount = request.getAmount();

        // Memeriksa apakah pengguna sumber dan pengguna tujuan sama
        if (sourceUsername.equals(destinationUsername)) {
            throw new IllegalArgumentException("Cannot transfer to the same user.");
        }

        // Mengambil objek pengguna sumber
        Optional<CustomerUser> sourceCustomerUserOptional = customerUserService.findCustomerUserByUsername(sourceUsername);
        if (sourceCustomerUserOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CustomerUser sourceCustomerUser = sourceCustomerUserOptional.get();
        Wallet sourceWallet = sourceCustomerUser.getWallet();

        // Memeriksa saldo cukup untuk transfer
        if (sourceWallet.getBalance().compareTo(transferAmount) < 0) {
            throw new IllegalArgumentException("Insufficient balance for transfer.");
        }

        // Mengambil objek pengguna tujuan
        Optional<CustomerUser> destinationCustomerUserOptional = customerUserService.findCustomerUserByUsername(destinationUsername);
        if (destinationCustomerUserOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CustomerUser destinationCustomerUser = destinationCustomerUserOptional.get();
        Wallet destinationWallet = destinationCustomerUser.getWallet();

        // Mengurangi saldo pengguna sumber dan meningkatkan saldo pengguna tujuan
        sourceWallet.decreaseBalance(transferAmount);
        destinationWallet.increaseBalance(transferAmount);

        // Membuat objek transaksi untuk transfer dari pengguna sumber
        Transaction sourceTransaction = Transaction.builder()
                .username(sourceUsername)
                .transactionType(TransactionType.OUT)
                .description("Transfer to " + destinationUsername)
                .amount(transferAmount)
                .wallet(sourceWallet)
                .build();

        // Menyimpan objek Wallet dan CustomerUser jika belum tersimpan
        // if (sourceWallet.getId() == null) {
        //     sourceWallet.setCustomerUser(sourceCustomerUser);
        //     walletService.createOne(sourceWallet);
        // }
        // if (destinationWallet.getId() == null) {
        //     destinationWallet.setCustomerUser(destinationCustomerUser);
        //     walletService.createOne(destinationWallet);
        // }

        // Menyimpan objek transaksi dari pengguna sumber
        Transaction savedSourceTransaction = transactionService.createOne(sourceTransaction);

        // Membuat objek transaksi untuk transfer ke pengguna tujuan
        Transaction destinationTransaction = Transaction.builder()
                .username(destinationUsername)
                .transactionType(TransactionType.IN)
                .description("Transfer from " + sourceUsername)
                .amount(transferAmount)
                .wallet(destinationWallet)
                .build();

        // Menyimpan objek transaksi ke pengguna tujuan
        // Transaction savedDestinationTransaction = transactionService.createOne(destinationTransaction);
        transactionService.createOne(destinationTransaction);

        // Mengonversi hasil transaksi tujuan menjadi respons
        TransactionResponse response = savedSourceTransaction.convertToResponse();
        return ResponseEntity.ok(response);
    }

    
}
