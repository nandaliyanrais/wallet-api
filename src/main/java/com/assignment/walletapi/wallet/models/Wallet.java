package com.assignment.walletapi.wallet.models;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import com.assignment.walletapi.customeruser.models.CustomerUser;
import com.assignment.walletapi.transaction.models.Transaction;
import com.assignment.walletapi.transaction.models.dto.TransactionResponse;
import com.assignment.walletapi.wallet.models.dto.WalletResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @Cascade(CascadeType.ALL)
    private CustomerUser customerUser;

    private BigDecimal balance;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    public WalletResponse convertToResponse() {
        List<TransactionResponse> transactionResponses = this.transactions.stream()
                                                            .sorted(Comparator.comparing(Transaction::getCreatedAt))
                                                            .map(Transaction::convertToResponse)
                                                            .toList();
        return WalletResponse.builder()
                .id(this.id)
                .balance(this.balance)
                .transactions(transactionResponses)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }

    public void decreaseBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid amount. Amount must be positive.");
        }

        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        balance = newBalance;
    }

    public void increaseBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid amount. Amount must be positive.");
        }

        balance = balance.add(amount);
    }
}
