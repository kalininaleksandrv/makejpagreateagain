package com.github.kalininaleksandrv.makejpagreateagain.controller;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping(path = "accounts")
    public ResponseEntity<Iterable<Account>> accounts() {
        var foundedAccounts = accountService.findAll();
        long count = StreamSupport.stream(foundedAccounts.spliterator(), false).count();
        if(count==0){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        /*
          this is N+1 representation - when we select all accounts we have 1 select
          then for each account.getClient().getName() separate query will be executed
          direct iteration was needed because response not contains Clients in each Account Json mapping
         */
        Iterator<Account> iter = foundedAccounts.iterator();
        iter.forEachRemaining(i -> log.info(i.getClient().getName()));
        return new ResponseEntity<>(foundedAccounts, HttpStatus.OK);
    }
}
