package com.github.kalininaleksandrv.makejpagreateagain.controller;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.projection.AccountView;
import com.github.kalininaleksandrv.makejpagreateagain.model.projection.BlockingAccountProjectionDTO;
import com.github.kalininaleksandrv.makejpagreateagain.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/")
@Slf4j
public class AccountController {

    // TODO: 07.09.2022 add @Transaction and pessimistic/optimistic lock
    // TODO: 19.11.2022 add @Transaction(readonly = true)
    // TODO: 19.11.2022 gets rid the OSIV problem
    private final AccountService accountService;

    @GetMapping(path = "accounts")
    public ResponseEntity<Iterable<Account>> accounts() {
        List<Account> accounts = new ArrayList<>();
        accountService.findAll().forEach(accounts::add);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /*
    since we're generify accountService.findBlocking method to work with projection, we must generify controller too
    param useCriteriaApi used only for demonstration property - it switches method of service to use
     */

    @GetMapping(path = "accounts/blocked")
    public ResponseEntity<Iterable<?>> blocked(@RequestParam boolean shortView,
                                               @RequestParam(required = false) boolean useCriteriaApi) {
        if (useCriteriaApi) {
            if (shortView) {
                return new ResponseEntity<>(accountService
                        .findBlockingCriteriaApproach(BlockingAccountProjectionDTO.class), HttpStatus.OK);
            }
            return new ResponseEntity<>(accountService.findBlockingCriteriaApproach(Account.class), HttpStatus.OK);
        }
        if (shortView) {
            return new ResponseEntity<>(accountService.findBlocking(AccountView.class), HttpStatus.OK);
        }
        return new ResponseEntity<>(accountService.findBlocking(Account.class), HttpStatus.OK);
    }

    @GetMapping(path = "account/{id}")
    public ResponseEntity<Account> account(@PathVariable Integer id) {
        Optional<Account> accountOpt = accountService.findById(id);
        return accountOpt.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }

    @PostMapping(path = "account")
    public ResponseEntity<Account> account(@RequestBody Account account) {
        return new ResponseEntity<>(accountService.saveAccount(account), HttpStatus.OK);
    }

    @PostMapping(path =  "account/{id}/balance")
    public ResponseEntity<Account> accountBalance(@PathVariable Integer toAccountId,
                                                  @RequestParam(required = false) Integer fromAccountId,
                                                  @RequestParam BigDecimal changingValue){
        return new ResponseEntity<>(accountService.updateBalance(fromAccountId, toAccountId, changingValue), HttpStatus.OK);
    }

}
