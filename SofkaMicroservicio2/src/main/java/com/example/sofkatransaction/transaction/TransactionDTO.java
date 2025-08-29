package com.example.sofkatransaction.transaction;

import com.example.sofkatransaction.account.Account;
import com.example.sofkatransaction.account.AccountType;
import com.example.sofkatransaction.shared.commons.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransactionDTO {
    private LocalDateTime createdAt;
    private String clientName;
    private Long accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Status status;
    private String transaction;
    private BigDecimal balance;

    public TransactionDTO(Transaction transaction, Account account, String clientName) {
        Map<String, Object> map = getTransactionValues(transaction);
        this.createdAt = transaction.getCreatedAt();
        this.clientName = clientName;
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType().getDescription();
        this.status = account.getStatus();
        this.initialBalance = (BigDecimal) map.get("originalValue");
        this.transaction = (String) map.get("transaction");
        this.balance = transaction.getBalance();


    }

    private Map<String, Object> getTransactionValues(Transaction transaction) {
        return switch (transaction.getTransactionType()) {
            case WITHDRAW -> withdrawTransaction(transaction);
            case DEPOSIT -> depositTransaction(transaction);
        };
    }

    private Map<String, Object> withdrawTransaction(Transaction transaction) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal originalValue = transaction.getBalance().add(transaction.getAmount());
        map.put("originalValue", originalValue);
        map.put("transaction", "-" + transaction.getAmount().toString());
        return map;
    }

    private Map<String, Object> depositTransaction(Transaction transaction) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal originalValue = transaction.getBalance().subtract(transaction.getAmount());
        map.put("originalValue", originalValue);
        map.put("transaction", "+" + transaction.getAmount().toString());
        return map;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
