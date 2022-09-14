package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;

public interface AccountService {
    Iterable<Account> findAll();
    Account saveAccount(Account account);

    Account findAccountById(int i);

    int countAccountsByCurrency(Currency currency);
}
