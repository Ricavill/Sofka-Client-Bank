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
    public Account getAccount(@PathVariable Long id) {
        return accountService.getClientAccountById(id);
    }

    @PostMapping("")
    public Account createAccount(@RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }

    @PatchMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody AccountRequest request) {
        return accountService.updateAccount(id, request);
    }

    @DeleteMapping("/{id}")
    public Account deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccount(id);

    }
}