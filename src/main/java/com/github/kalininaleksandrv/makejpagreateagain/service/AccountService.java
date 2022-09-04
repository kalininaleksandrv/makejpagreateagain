package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;

public interface AccountService {
    Iterable<Account> findAll();
    Account saveAccount(Account account);

}
