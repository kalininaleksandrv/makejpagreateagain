package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.exception.AccountProcessingException;
import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import com.github.kalininaleksandrv.makejpagreateagain.model.projection.BlockingAccountProjectionDTO;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountQueryRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountQueryRepository accountQueryRepository;
    private final ClientRepository clientRepository; // TODO: 15.02.2023 change to ClientService
    private final EntityManager entityManager; // TODO: 06.02.2023 move out to external @Repository

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
    @Transactional
    public Account saveAccount(Account account) {

        Optional<Client> clientFromDb = checkAndFindClientFromDb(account.getClient());
        Optional<Account> accountFromDb = checkAndFindAccountFromDb(account.getId());
        if(accountFromDb.isEmpty() && clientFromDb.isEmpty()){
            clientRepository.save(account.getClient());
        } else {
            if(clientFromDb.isPresent() && clientFromDb.get().getId().equals(account.getClient().getId())){
                /*
                 we must remain save method despite @Transactional if we want
                 update existing client due update existing account
                */
                clientRepository.save(account.getClient());
            } else {
                throw new AccountProcessingException("unable to move existing account to new client");
            }
        }
        /*
        we must remain save method despite @Transactional for the new account cases
         */
        return accountRepository.save(account);
    }

    private Optional<Account> checkAndFindAccountFromDb(Integer id) {
        if(id == null) return Optional.empty();
        return accountRepository.findById(id);
    }

    private Optional<Client> checkAndFindClientFromDb(Client client) {
        if(client == null || client.getId() == null) return Optional.empty();
        return clientRepository.findById(client.getId());
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
    public int countAccountsLessThenAmount(BigDecimal amount) {
        return accountQueryRepository.findNumberOfAccountsLessThenAmount(amount);
    }

    @Override
    public List<Account> findAllByAmountAndCurrency(BigDecimal amount, Currency currency) {
        return accountQueryRepository.findByAmountAndCurrency(amount, currency);
    }

    @Override
    public List<Account> findByCurrencyAndSort(Currency currency, Sort sort) {
        return accountQueryRepository.findByCurrencyAndSort(currency, sort);
    }

    @Override
    public <T> List<T> findBlocking(Class<T> type) {

        /*
        simple method to work with projection - List<AccountView> not very convenient because of typification,
        so we will be able to turn it to List<T> and add Class<T> parameter:
        <T> List<T> findByBlockedTrue(Class<T> type); in repo
        but since we need entity graph to fetch Account and do unable to use entity graph to fetch AccountView
        we must create 2 separate methods in repo (workaround - overloads it by signature)
        first for return ANY projections which not contains Client field
        second for return whole Account object contains Client
        OR use CriteriaApi and additional Entity for projection (see second approach below)
        */

        // projections is interfaces, whole Account.class is not
        if (type.isInterface()) {
            return accountRepository.findByBlockedTrue(type);
        }
        return accountRepository.findByBlockedTrue();
    }

    /*
    this is CriteriaApi approach, see the explanation under
    in this approach we are using plain DTO instead of interface to create projection
    */
    public <T> List<T> findBlockingCriteriaApproach(Class<T> type) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<Account> root = criteria.from(Account.class);
        criteria.where(
                builder.equal(root.get("blocked"), true)
        );
        // we add projection extraction only when we need projection to return
        if (type.isAssignableFrom(BlockingAccountProjectionDTO.class)) {
            criteria.multiselect(root.get("id"), root.get("blocked"), root.get("blockingReason"));
        }
        TypedQuery<T> query = entityManager.createQuery(criteria);
        //we add entity graph only when we need base class to return
        if (type.isAssignableFrom(Account.class)) {
            EntityGraph<?> entityGraph = entityManager.getEntityGraph(Account.ACCOUNT_CLIENT_ENTITY_GRAPH);
            query.setHint("javax.persistence.loadgraph", entityGraph);
        }
        return query.getResultList();
    }

    @Override
    public Optional<Account> findById(Integer id) {
        return accountRepository.findById(id);
    }

    @Override
    /*
    1 in a concurrent modification regime @Transaction annotation itself does not help since isolation level by default is
    ReadCommitted which allows 2nd transaction read value from account when 1st already start to change it
    2 @Transactional(isolation = Isolation.REPEATABLE_READ) - correct behavior but a lot of exceptions,
    can add @Retryable to all transactions eventually will be executed, don't forget to add @EnableRetry on Application.class
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, value = SQLException.class)
    public Account updateBalance(Integer fromAccountId, Integer toAccountId, BigDecimal changingValue) {

        Optional<Account> to = accountRepository.findById(toAccountId);
        Account from = accountRepository
                .findById(fromAccountId).orElseThrow(() -> new AccountProcessingException("no such account available: " + fromAccountId));
        //for the brevity sake check for blocking status is omitted
        // TODO: 03.01.2023 currency conversion
        if (from.getAmount().compareTo(changingValue) < 0) {
            throw new AccountProcessingException("not enough money on source account");
        }
        from.setAmount(from.getAmount().subtract(changingValue));
        to.orElseThrow(() -> new AccountProcessingException("no such account available: " + toAccountId)).setAmount(to.get().getAmount().add(changingValue));
        return to.get();
    }
}
