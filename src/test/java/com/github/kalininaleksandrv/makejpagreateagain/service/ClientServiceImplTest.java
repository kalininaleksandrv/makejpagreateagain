package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientServiceImplTest {

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    ClientRepository clientRepository;

    @Test
    void saveClient() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        client.getAccounts().add(account);
        Client res = clientService.saveClient(client);
        assertEquals(1, res.getId());
    }

    @Test
    void saveClientWithId() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        client.getAccounts().add(account);
        clientRepository.save(client);
        client.setName("Peter");
        Client res = clientService.saveClient(client);
        assertEquals("Peter", res.getName());
    }

    @Test
    void findClientById() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        client.getAccounts().add(account);
        Integer id = clientRepository.save(client).getId();
        Optional<Client> clientById = clientService.findClientById(id);
        assertNotNull(clientById.get());
    }
}