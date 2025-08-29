package com.example.sofkatransaction.account;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cuentas")
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    public Account getClient(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PostMapping("")
    public Account createAccount(@RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }
}