package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.exception.AccountProcessingException;
import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import com.github.kalininaleksandrv.makejpagreateagain.model.projection.AccountView;
import com.github.kalininaleksandrv.makejpagreateagain.model.projection.BlockingAccountProjectionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest extends UserAndAccountBaseApplicationTests {

    @Autowired
    AccountServiceImpl accountService;

    @Test
    void findAll() {
        Iterable<Account> savedAccount = accountService.findAll();
        assertEquals(10, savedAccount.spliterator().estimateSize());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
        //clear context to delete old sequences
    void findById() {
        Optional<Account> byId = accountService.findById(1);
        assertTrue(byId.isPresent());
        assertEquals(1, byId.get().getId());
        assertEquals(1, byId.get().getClient().getId());
    }

    @Test
    void countAccountsByCurrency() {
        int res = accountService.countAccountsByCurrency(Currency.USD);
        assertEquals(5, res);
    }

    @Test
    void findAllByAmountAndCurrency() {
        List<Account> res = accountService.findAllByAmountAndCurrency(BigDecimal.valueOf(500), Currency.EUR);
        assertAll(
                () -> assertEquals(3, res.size()),
                () -> assertTrue(res.get(0).getAmount().intValue()>=500),
                () -> assertTrue(res.get(1).getAmount().intValue()>=500),
                () -> assertTrue(res.get(2).getAmount().intValue()>=500)
        );
    }

    @Test
    void countAccountsLessThenAmount() {
        int res = accountService.countAccountsLessThenAmount(BigDecimal.valueOf(501));
        assertEquals(res, 5);
    }

    @Test
    void findByAmountAndSort() {
        List<Account> res = accountService.findByCurrencyAndSort(Currency.EUR,
                JpaSort.by(Sort.Direction.DESC, "amount"));
        assertAll(
                () -> assertEquals(5, res.size()),
                () -> assertTrue(res.get(0).getAmount().intValue()>=900),
                () -> assertTrue(res.get(1).getAmount().intValue()>=700),
                () -> assertTrue(res.get(2).getAmount().intValue()>=500)
        );
    }

    @Test
    void saveAccountNewClient() {
        Account account = new Account();
        account.setAmount(BigDecimal.valueOf(100));
        account.setCurrency(Currency.USD);
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        account.setClient(client);
        Account res = accountService.saveAccount(account);
        Client res_c = clientRepository.findByName("Vasily");
        assertEquals(100, res.getAmount().intValue());
        assertNotNull(res_c.getId());
    }

    @Test
    void saveAccountExistingClient() {
        Account account = new Account();
        account.setAmount(BigDecimal.valueOf(100));
        account.setCurrency(Currency.USD);
        Client savedClient = clientRepository.findByName("First Client");
        savedClient.setName("name which must be ignored");
        account.setClient(savedClient);
        Account res = accountService.saveAccount(account);
        assertEquals(100, res.getAmount().intValue());
        assertEquals("First Client", res.getClient().getName());
    }

    @Test
    void updateExistingAccountWithExistingClient(){
        Account savedAccount = accountService.findAll().iterator().next();
        savedAccount.setAmount(BigDecimal.valueOf(100000));
        savedAccount.getClient().setName("name which must be ignored");
        Account updatedAccount = accountService.saveAccount(savedAccount);
        assertEquals(100000, updatedAccount.getAmount().intValue());
        assertEquals(savedAccount.getId(), updatedAccount.getId());
        assertEquals(savedAccount.getClient().getId(), clientRepository.findAll().iterator().next().getId());
    }

    @Test
    void updateExistingAccountWithWrongClient(){
        Account account = accountService.findAll().iterator().next();
        Client client = new Client();
        client.setId(100);
        account.setClient(client);
        assertThrows(AccountProcessingException.class, () -> accountService.saveAccount(account));
    }

    @Test
    void updateExistingAccountWithNewClient() {
        Account account = accountService.findAll().iterator().next();
        Client client = new Client();
        client.setId(null);
        account.setClient(client);
        assertThrows(AccountProcessingException.class, () -> accountService.saveAccount(account));
    }
    /*
    since findByBlockedTrue() is generify we can fetch both - Account and AccountView from the same repo
     */

    @Test
    void findBlocking() {
        List<AccountView> accountProjections = accountService.findBlocking(AccountView.class);
        assertAll(
                () -> assertEquals(3, accountProjections.size()),
                () -> assertEquals("fraud", accountProjections.get(0).getBlockingReason()),
                () -> assertEquals("fraud", accountProjections.get(1).getBlockingReason()),
                () -> assertEquals("fraud", accountProjections.get(2).getBlockingReason())
        );

        List<Account> accounts = accountService.findBlocking(Account.class);
        assertAll(
                () -> assertEquals(3, accounts.size()),
                () -> assertEquals("fraud", accounts.get(0).getBlockingReason()),
                () -> assertEquals(300, accounts.get(0).getAmount().intValue())
        );
    }

    @Test
    void findBlockingCriteriaApproach() {
        List<BlockingAccountProjectionDTO> accountProjections = accountService
                .findBlockingCriteriaApproach(BlockingAccountProjectionDTO.class);

        assertAll(
                () -> assertEquals(3, accountProjections.size()),
                () -> assertEquals("fraud", accountProjections.get(0).getBlockingReason()),
                () -> assertEquals("fraud", accountProjections.get(1).getBlockingReason()),
                () -> assertEquals("fraud", accountProjections.get(2).getBlockingReason())
        );

        List<Account> accounts = accountService.findBlockingCriteriaApproach(Account.class);
        assertAll(
                () -> assertEquals(3, accounts.size()),
                () -> assertEquals("fraud", accounts.get(0).getBlockingReason()),
                () -> assertEquals(300, accounts.get(0).getAmount().intValue())
        );
    }

    @Test
    void updateBalanceNotEnoughMoney() {
        List<Account> savedAccount = StreamSupport.stream(accountService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (savedAccount.size() < 2) {
            throw new RuntimeException("unable to test - number of accounts less then two");
        }
        Account from = savedAccount.get(0);
        Account to = savedAccount.get(1);
        AccountProcessingException ex = assertThrows(AccountProcessingException.class, () -> accountService
                .updateBalance(from.getId(), to.getId(), from.getAmount().add(new BigDecimal("0.01"))));
        assertEquals("not enough money on source account", ex.getMessage());
    }

    @Test
    void updateBalanceSuccess() {
        List<Account> savedAccount = StreamSupport.stream(accountService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        if (savedAccount.size() < 2) {
            throw new RuntimeException(this.getClass().getSimpleName() +
                    "unable to test - number of accounts less then two");
        }
        Account from = savedAccount.get(0);
        Account to = savedAccount.get(1);
        double oldValue = to.getAmount().doubleValue();
        accountService.updateBalance(from.getId(), to.getId(), from.getAmount());
        Account fromAfterOperation = accountRepository.findById(from.getId())
                .orElseThrow(() -> new RuntimeException(this.getClass().getSimpleName() +
                        "source account not found after operation"));
        Account toAfterOperation = accountRepository.findById(to.getId())
                .orElseThrow(() -> new RuntimeException(this.getClass().getSimpleName() +
                        "target account not found after operation"));
        assertEquals(0, fromAfterOperation.getAmount().doubleValue());
        assertEquals(oldValue + from.getAmount().longValue(), toAfterOperation.getAmount().doubleValue());
    }
}