package com.github.kalininaleksandrv.makejpagreateagain.controller;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping(path = "accounts")
    public ResponseEntity<Iterable<Account>> accounts() {
        List<Account> accounts = new ArrayList<>();
        accountService.findAll().forEach(accounts::add);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping(path = "account")
    public ResponseEntity<Account> account(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.saveAccount(account), HttpStatus.OK);
    }

    // TODO: 09.08.2022 add method to add new account and to change existing account
}
