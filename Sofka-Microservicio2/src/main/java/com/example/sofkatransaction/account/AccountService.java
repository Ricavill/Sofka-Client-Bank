package com.example.sofkatransaction.account;

import com.example.sofkatransaction.client.Client;
import com.example.sofkatransaction.shared.commons.DateRange;
import com.example.sofkatransaction.shared.commons.Status;
import com.example.sofkatransaction.shared.config.exceptions.EntityNotFoundException;
import com.example.sofkatransaction.shared.config.security.auth.AuthService;
import com.example.sofkatransaction.shared.services.ms1.MicroService1RestAPI;
import com.example.sofkatransaction.transaction.TransactionRepository;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AuthService authService;
    private final MicroService1RestAPI microService1RestAPI;

    public AccountService(AccountRepository accountRepository,
                          TransactionRepository transactionRepository,
                          AuthService authService,
                          MicroService1RestAPI microService1RestAPI) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.authService = authService;
        this.microService1RestAPI = microService1RestAPI;
    }

    public Account getAccountById(Long id) {
        return accountRepository.findAccountById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " not found"));
    }

    public Account getAccountByAccountNumber(Long accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException("Account with account number " + accountNumber + " not found"));
    }


    public Account createAccount(AccountRequest accountRequest) {
        Client client = authService.getAuthenticatedClient();
        client = microService1RestAPI.getClientById(client.getId());
        if (client == null || client.getStatus() != Status.ACTIVE.getCode()) {
            throw new ValidationException("Client doesn't exist or is not active");
        }
        accountRequest.setClientId(client.getId());
        Account account = new Account(accountRequest);
        account = accountRepository.save(account);
        return account;
    }

    public Account updateAccount(Account account, AccountRequest accountRequest) {
        account.update(accountRequest);
        return accountRepository.save(account);
    }

    public List<Account> getAccountReport(Long clientId, DateRange dateRange) {
        return accountRepository.findAccountsExtendedByClientId(clientId,dateRange.getStartDate(),dateRange.getEndDate());
    }
}
