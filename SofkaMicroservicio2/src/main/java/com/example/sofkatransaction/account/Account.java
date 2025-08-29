package com.example.sofkatransaction.account;

import com.example.sofkatransaction.auditing.AuditableEntity;
import com.example.sofkatransaction.shared.commons.PropertiesUtils;
import com.example.sofkatransaction.shared.commons.Status;
import com.example.sofkatransaction.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.Where;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class Account extends AuditableEntity<Long> {
    private static final String[] READ_ONLY_PROPERTIES = {"accountNumber", "clientId", "accountType",};

    @Column(nullable = false, insertable = false, updatable = false)
    @ColumnDefault("nextval('account_number_seq')")
    @Generated()
    private Long accountNumber;

    private Long clientId;

    /*Como es un poco raro tener en base el valor de un type como string como se decia en el documento mejor lo trate
    como enum*/
    private AccountType accountType;
    private BigDecimal initialBalance;
    private Status status;

    @JsonManagedReference
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Account() {
    }

    public Account(AccountRequest accountRequest) {
        clientId = accountRequest.getClientId();
        accountType = accountRequest.getAccountType();
        initialBalance = accountRequest.getInitialBalance();
        status = Status.ACTIVE;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void update(AccountRequest request) {
        String[] nullProperties = PropertiesUtils.getNullPropertyNames(request);
        String[] ignoreProperties = Stream.of(nullProperties, READ_ONLY_PROPERTIES)
                .flatMap(Stream::of)
                .toArray(String[]::new);
        BeanUtils.copyProperties(request, this, ignoreProperties);
    }
}
