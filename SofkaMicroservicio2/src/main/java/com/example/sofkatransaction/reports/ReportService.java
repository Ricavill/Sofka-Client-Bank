package com.example.sofkatransaction.reports;

import com.example.sofkatransaction.account.Account;
import com.example.sofkatransaction.client.Client;
import com.example.sofkatransaction.shared.commons.DateRange;
import com.example.sofkatransaction.shared.config.security.auth.AuthService;
import com.example.sofkatransaction.shared.services.ms1.MicroService1RestAPI;
import com.example.sofkatransaction.transaction.Transaction;
import com.example.sofkatransaction.transaction.TransactionDTO;
import com.example.sofkatransaction.transaction.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final TransactionRepository transactionRepository;
    private final AuthService authService;
    private final MicroService1RestAPI microService1RestAPI;

    public ReportService(TransactionRepository transactionRepository, AuthService authService, MicroService1RestAPI microService1RestAPI) {
        this.transactionRepository = transactionRepository;
        this.authService = authService;
        this.microService1RestAPI = microService1RestAPI;
    }

    public Page<TransactionDTO> getAccountsReport(DateRange dateRange, Pageable pageable) {
        //Uso el clientId del token por que se supone que el mismo cliente consulta sus movimientos, como en una
        //banca virtual.
        Client client = authService.getAuthenticatedClient();
        client = microService1RestAPI.getClientById(client.getId());
        return transactionRepository.findAccountsExtendedByClientId(client.getId(), client.getName(),
                dateRange.getStartDate(), dateRange.getEndDate(), pageable);
    }


}
