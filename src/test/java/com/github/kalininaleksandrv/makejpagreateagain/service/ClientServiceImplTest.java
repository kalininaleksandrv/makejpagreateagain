package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceImplTest extends UserAndAccountBaseApplicationTests {

    @Autowired
    ClientServiceImpl clientService;

    @Test
    void saveClient() {
        Account account = new Account();
        account.setAmount(BigDecimal.valueOf(100));
        account.setCurrency(Currency.USD);
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        client.setAccounts(accounts);
        Client res = clientService.saveClient(client);
        assertNotNull(res.getId());
    }

    @Test
    @Transactional
    /*
    we add @Transactional annotation to avoid "Lazy init" exception on Accounts fetching,
    despite in a real call we fetch accounts without transactional (using named entity graph)
     */
    void saveClientWithId() {
        Integer id = StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .findFirst().orElseThrow().getId();
        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException(this.getClass().getSimpleName()));
        clientRepository.save(client);
        client.setName("Peter");
        Client res = clientService.saveClient(client);
        assertEquals("Peter", res.getName());
    }

    @Test
    void findClientById() {
        Integer id = StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .findFirst().orElseThrow().getId();
        Optional<Client> clientById = clientService.findClientById(id);
        assertEquals(id, clientById.orElseThrow(() -> new RuntimeException(this.getClass().getSimpleName())).getId());
    }

    @Test
    void saveClientWithAge() {
        Account account = new Account();
        account.setAmount(BigDecimal.valueOf(100));
        account.setCurrency(Currency.USD);
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        client.setAccounts(accounts);
        clientService.saveClientWithAge(client, 100);
        Client fetchedClient = clientRepository.findByName("Vasily");
        assertEquals(100, fetchedClient.getAge());
    }

    @Test
    void getAllWithAmtMoreThen() {
        //create additional client with account amount more than 10 000
        Account account = new Account();
        account.setAmount(BigDecimal.valueOf(10001));
        account.setCurrency(Currency.USD);
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        client.setAccounts(accounts);
        clientService.saveClient(client);
        List<Client> allWithAmtMoreThen = clientService.getAllWithAmtMoreThen(BigDecimal.valueOf(10000));
        assertEquals(1, allWithAmtMoreThen.size());
        assertEquals("Vasily", allWithAmtMoreThen.get(0).getName());
    }
}