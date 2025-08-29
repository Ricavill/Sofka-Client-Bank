package com.example.sofkatransaction.transaction;

import com.example.sofkatransaction.account.Account;
import com.example.sofkatransaction.account.AccountRequest;
import com.example.sofkatransaction.account.AccountService;
import com.example.sofkatransaction.shared.config.exceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public Transaction createTransaction(TransactionRequest transactionRequest) {

        fetchAccount(transactionRequest);
        checkTransactionRequest(transactionRequest);
        return doOperation(transactionRequest);
    }

    private Transaction doOperation(TransactionRequest transactionRequest) {
        Transaction transaction = switch (transactionRequest.getTransactionType()) {
            case WITHDRAW -> withdrawTransaction(transactionRequest);
            case DEPOSIT -> depositTransaction(transactionRequest);
        };
        transaction = transactionRepository.save(transaction);
        Account account = transactionRequest.getAccount();
        accountService.updateAccount(account, new AccountRequest(transactionRequest.getBalance()));
        return transaction;
    }


    private Transaction withdrawTransaction(TransactionRequest transactionRequest) {
        BigDecimal amount = transactionRequest.getAmount();
        BigDecimal initialBalance = transactionRequest.getAccount().getInitialBalance();
        BigDecimal newBalance = initialBalance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Saldo no disponible");
        }
        transactionRequest.setBalance(newBalance);
        return new Transaction(transactionRequest);
    }

    private Transaction depositTransaction(TransactionRequest transactionRequest) {
        BigDecimal amount = transactionRequest.getAmount();
        BigDecimal initialBalance = transactionRequest.getAccount().getInitialBalance();
        BigDecimal newBalance = initialBalance.add(amount);
        transactionRequest.setBalance(newBalance);
        return new Transaction(transactionRequest);
    }

    private void checkTransactionRequest(TransactionRequest transactionRequest) {
        BigDecimal amount = transactionRequest.getAmount();
        BigDecimal initialBalance = transactionRequest.getAccount().getInitialBalance();
        if (amount == null || initialBalance == null) {
            throw new ValidationException("Amount and initial balance cannot be null");
        }
        return;
    }

    public void fetchAccount(TransactionRequest transactionRequest) {
        transactionRequest.setAccount(accountService.getAccountByAccountNumber(transactionRequest.getAccountNumber()));
    }
}
