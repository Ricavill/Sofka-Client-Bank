package com.example.sofkatransaction.transaction;

import com.example.sofkatransaction.account.Account;
import com.example.sofkatransaction.auditing.AuditableEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private TransactionType transactionType;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    private BigDecimal amount;
    private BigDecimal balance;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    protected LocalDateTime createdAt;

    public Transaction() {
    }

    public Transaction(TransactionRequest transactionRequest) {
        this.transactionType = transactionRequest.getTransactionType();
        this.account = transactionRequest.getAccount();
        this.amount = transactionRequest.getAmount();
        this.balance = transactionRequest.getBalance();
    }


    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
