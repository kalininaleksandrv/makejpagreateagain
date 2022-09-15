package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.exception.AccountProcessingException;
import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountQueryRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;
    private final AccountQueryRepository accountQueryRepository;
    private final ClientRepository clientRepository;
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

    @Override
    public Account saveAccount(Account account) {
        // TODO: 04.09.2022 it must be impossible to add new client without ID on existing account
        if(account.getClient() == null) throw new AccountProcessingException("account must contain client info");
        if(account.getClient().getId() != null){
            Optional<Client> clientFromDb = clientRepository.findById(account.getClient().getId());
            /*
            if the Client object from request contains changes, they will not be saved,
            because object from db will replace it
             */
            account.setClient(clientFromDb.orElseThrow(() ->
                    new AccountProcessingException("client, which has been referenced by account - not found")));
        } else {
            if(account.getId()!=null) throw new AccountProcessingException("unable to create new client with existing account");
                /*
                to avoid "transient instance exception" in saveAccount method we must either save new client
                before save account explicitly or add CascadeType.PERSIST to Client relation in account for Hibernate
                perform save operation for us
                but since new client have no Id, adding CascadeType.PERSIST will break work with already existing clients
                with "detached entity passed to persist" exception
                The exception comes as hibernate trying to persist associated products when you save reservation.
                Persisting the products is only success if they have no id because id of Client is annotated
                @GeneratedValue(strategy = GenerationType.SEQUENCE)
                */
            clientRepository.save(account.getClient());
        }
        return accountRepository.save(account);
    }

    @Override
    public Account findAccountById(int i) {
        return accountRepository.findById(i).orElseThrow(() ->
                new AccountProcessingException("no account with such id founded"));
    }

    @Override
    public int countAccountsByCurrency(Currency currency) {
        return accountQueryRepository.findNumberOfAccountsByCurrency(currency);
    }

    @Override
    public int countAccountsLessThenAmount(int amount) {
        return accountQueryRepository.findNumberOfAccountsLessThenAmount(amount);
    }

    @Override
    public List<Account> findAllByAmountAndCurrency(int amount, Currency currency) {
        return accountQueryRepository.findByAmountAndCurrency(amount, currency);
    }

    @Override
    public List<Account> findByCurrencyAndSort(Currency currency, Sort sort) {
        return accountQueryRepository.findByCurrencyAndSort(currency, sort);
    }
}
