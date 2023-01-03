package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    Iterable<Account> findAll();
    Account saveAccount(Account account);

    Account findAccountById(int i);

    int countAccountsByCurrency(Currency currency);

    int countAccountsLessThenAmount(int amount);

    List<Account> findAllByAmountAndCurrency(int amount, Currency currency);

    List<Account> findByCurrencyAndSort(Currency currency, Sort sort);

    <T> List<T> findBlocking(Class<T> type);

    <T> List<T> findBlockingCriteriaApproach(Class<T> type);

    Optional<Account> findById(Integer id);
}
