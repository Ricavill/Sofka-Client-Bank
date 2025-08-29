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

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository, AuthService authService, MicroService1RestAPI microService1RestAPI) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.authService = authService;
        this.microService1RestAPI = microService1RestAPI;
    }

    public Account getClientAccountById(Long id) {
        Client client = authService.getAuthenticatedClient();
        return accountRepository.findClientAccountById(id, client.getId())
                .orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " not found"));

    }

    public Account getAccountById(Long id) {
        return accountRepository.findAccountById(id).orElseThrow(() -> new EntityNotFoundException("Account with id " + id + " not found"));
    }

    public Account getAccountByAccountNumber(Long accountNumber) {
        Client client = authService.getAuthenticatedClient();
        return accountRepository.findAccountByAccountNumber(accountNumber, client.getId())
                .orElseThrow(() -> new EntityNotFoundException("Account with account number " + accountNumber + " not found"));
    }


    //Por como esta estruturado el ejercicio se asumo que el cliente es el que procesa las cosas, no un usuario aparte.
    //En otras palabras una banca virtual.
    public Account createAccount(AccountRequest accountRequest) {
        Client client = authService.getAuthenticatedClient();
        checkClientStatus(client);
        accountRequest.setClientId(client.getId());
        Account account = new Account(accountRequest);
        account = accountRepository.save(account);
        return account;
    }

    public Account updateAccount(Long accountId, AccountRequest accountRequest) {
        Account account = getAccountById(accountId);
        updateAccount(account, accountRequest);
        return accountRepository.save(account);
    }

    public Account updateAccount(Account account, AccountRequest accountRequest) {
        Client client = authService.getAuthenticatedClient();
        checkClientStatus(client);
        account.update(accountRequest);
        return accountRepository.save(account);
    }

    public Account deleteAccount(Long accountId) {
        Client client = authService.getAuthenticatedClient();
        checkClientStatus(client);
        Account account = getAccountById(accountId);
        account.setStatus(Status.DISABLED);
        account.delete();
        return accountRepository.save(account);
    }

    public List<Account> getAccountReport(Long clientId, DateRange dateRange) {
        Client client = authService.getAuthenticatedClient();
        checkClientStatus(client);

        return accountRepository.findAccountsExtendedByClientId(clientId, dateRange.getStartDate(), dateRange.getEndDate());
    }

    private void checkClientStatus(Client client) {
        client = microService1RestAPI.getClientById(client.getId());
        if (client == null || client.getStatus() != Status.ACTIVE.getCode()) {
            throw new ValidationException("Client doesn't exist or is not active");
        }
    }
}
