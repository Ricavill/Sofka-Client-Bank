package com.example.sofkatransaction.account;

import com.example.sofkatransaction.shared.commons.Status;

import java.math.BigDecimal;

public class AccountRequest {
    private Long clientId;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private Status status;

    public AccountRequest() {
    }

    public AccountRequest(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
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
}
