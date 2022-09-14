package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.exception.AccountProcessingException;
import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceImplTest extends UserAndAccountBaseApplicationTests {

    @Autowired
    AccountServiceImpl accountService;

    @Test
    @Order(1)
    void findAll() {
        Iterable<Account> savedAccount = accountService.findAll();
        assertEquals(10, savedAccount.spliterator().estimateSize());
    }

    @Test
    @Order(2)
    void saveAccountNewClient() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency(Currency.USD);
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        account.setClient(client);
        Account res = accountService.saveAccount(account);
        Client res_c = clientRepository.findByName("Vasily");
        assertEquals(100, res.getAmount());
        assertNotNull(res_c.getId());
    }

    @Test
    @Order(3)
    void saveAccountExistingClient() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency(Currency.USD);
        Client savedClient = clientRepository.findByName("First Client");
        savedClient.setName("name which must be ignored");
        account.setClient(savedClient);
        Account res = accountService.saveAccount(account);
        assertEquals(100, res.getAmount());
        assertEquals("First Client", res.getClient().getName());
    }

    @Test
    @Order(4)
    void updateExistingAccountWithExistingClient(){
        Account savedAccount = accountService.findAll().iterator().next();
        savedAccount.setAmount(100_000);
        savedAccount.getClient().setName("name which must be ignored");
        Account updatedAccount = accountService.saveAccount(savedAccount);
        assertEquals(100000, updatedAccount.getAmount());
        assertEquals(savedAccount.getId(), updatedAccount.getId());
        assertEquals(savedAccount.getClient().getId(), clientRepository.findAll().iterator().next().getId());
    }

    @Test
    @Order(5)
    void updateExistingAccountWithWrongClient(){
        Account account = accountService.findAll().iterator().next();
        Client client = new Client();
        client.setId(100);
        account.setClient(client);
        assertThrows(AccountProcessingException.class, () -> accountService.saveAccount(account));
    }

    @Test
    @Order(6)
    void updateExistingAccountWithNewClient(){
        Account account = accountService.findAll().iterator().next();
        Client client = new Client();
        client.setId(null);
        account.setClient(client);
        assertThrows(AccountProcessingException.class, () -> accountService.saveAccount(account));
    }

    @Test
    void countAccountsByCurrency() {
        int res = accountService.countAccountsByCurrency(Currency.USD);
        assertEquals(5, res);
    }

    @Test
    void findAllByAmountAndCurrency() {
        List<Account> res = accountService.findAllByAmountAndCurrency(500, Currency.EUR);
        assertAll(
                () -> assertEquals(3, res.size()),
                () -> assertTrue(res.get(0).getAmount()>=500),
                () -> assertTrue(res.get(1).getAmount()>=500),
                () -> assertTrue(res.get(2).getAmount()>=500)
        );
    }
}