package dev.socialnetwork.controller;

import dev.socialnetwork.entity.Account;
import dev.socialnetwork.exception.AccountNotFoundError;
import dev.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Account> getAccounts() {
        return accountService.findAll();
    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteById(@PathVariable Long id) {
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Account getAccountById(@PathVariable("id") long id) {
        return accountService.findById(id).orElseThrow(AccountNotFoundError::new);
    }

}
