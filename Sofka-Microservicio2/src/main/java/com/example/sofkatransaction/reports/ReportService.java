package com.example.sofkatransaction.reports;

import com.example.sofkatransaction.account.Account;
import com.example.sofkatransaction.account.AccountService;
import com.example.sofkatransaction.client.Client;
import com.example.sofkatransaction.shared.commons.DateRange;
import com.example.sofkatransaction.shared.config.security.auth.AuthService;
import com.example.sofkatransaction.shared.services.ms1.MicroService1RestAPI;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportService {
    private final AccountService accountService;
    private final AuthService authService;
    private final MicroService1RestAPI microService1RestAPI;

    public ReportService(AccountService accountService, AuthService authService, MicroService1RestAPI microService1RestAPI) {
        this.accountService = accountService;
        this.authService = authService;
        this.microService1RestAPI = microService1RestAPI;
    }

    public List<Account> getAccountsReport(DateRange dateRange) {
        //Uso el clientId del token por que se supone que el mismo cliente consulta sus movimientos
        Client client = authService.getAuthenticatedClient();
        return accountService.getAccountReport(client.getId(),dateRange);
    }


}
