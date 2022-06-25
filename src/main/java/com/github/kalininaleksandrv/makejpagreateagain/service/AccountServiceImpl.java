package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    @Override
    public Iterable<Account> findAll() {
        Iterable<Account> allAccounts = accountRepository.findAll();
        /*
          this is N+1 representation - when we select all accounts we have 1 select
          then for each account.getClient().getName() separate query will be executed
          direct iteration was needed because response not contains Clients in each Account Json mapping
          n+1 problem solves with entity graph
         */
        Iterator<Account> iter = allAccounts.iterator();
        iter.forEachRemaining(i -> log.info(i.getClient().getName()));
        return allAccounts;
    }
}
