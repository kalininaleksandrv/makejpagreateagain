package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.exception.AccountProcessingException;
import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountServiceImplTest {

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    ClientRepository clientRepository;

    @Test
    void findAll() {

    }

    @Test
    void saveAccountNewClient() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        account.setClient(client);
        Account res = accountService.saveAccount(account);
        Client res_c = clientRepository.findClientByName("Vasily");
        assertEquals(100, res.getAmount());
        assertNotNull(res_c.getId());
    }

    @Test
    void saveAccountExistingClient() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        Client savedClient = clientRepository.save(client);
        savedClient.setName("name which must be ignored");
        account.setClient(savedClient);
        Account res = accountService.saveAccount(account);
        assertEquals(100, res.getAmount());
        assertEquals("Vasily", res.getClient().getName());
    }

    @Test
    void updateExistingAccountWithExistingClient(){
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        account.setClient(client);
        Account savedAccount = accountService.saveAccount(account);
        savedAccount.setAmount(100_000);
        savedAccount.getClient().setName("name which must be ignored");
        Account updatedAccount = accountService.saveAccount(savedAccount);
        assertEquals(100000, updatedAccount.getAmount());
        assertEquals(savedAccount.getId(), updatedAccount.getId());

        List<Client> result = new ArrayList<>();
        clientRepository.findAll().forEach(result::add);
        assertEquals(1, result.size());
        assertEquals(savedAccount.getClient().getId(), result.get(0).getId());
    }

    @Test
    void updateExistingAccountWithWrongClient(){
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        account.setClient(client);
        Account savedAccount = accountService.saveAccount(account);
        client.setId(100);
        assertThrows(AccountProcessingException.class, () -> accountService.saveAccount(savedAccount));
    }

    @Test
    void updateExistingAccountWithNewClient(){
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        account.setClient(client);
        Account savedAccount = accountService.saveAccount(account);
        client.setId(null);
        assertThrows(AccountProcessingException.class, () -> accountService.saveAccount(savedAccount));
    }
}