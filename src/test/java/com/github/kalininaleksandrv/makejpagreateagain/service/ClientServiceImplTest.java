package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientServiceImplTest extends UserAndAccountBaseApplicationTests {

    @Autowired
    ClientServiceImpl clientService;

    @Test
    void saveClient() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
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
        Client client = clientRepository.findById(1).orElseThrow(() -> new RuntimeException(this.getClass().getSimpleName()));
        clientRepository.save(client);
        client.setName("Peter");
        Client res = clientService.saveClient(client);
        assertEquals("Peter", res.getName());
    }

    @Test
    void findClientById() {
        Optional<Client> clientById = clientService.findClientById(1);
        assertEquals(1, clientById.orElseThrow(() -> new RuntimeException(this.getClass().getSimpleName())).getId());
    }
}