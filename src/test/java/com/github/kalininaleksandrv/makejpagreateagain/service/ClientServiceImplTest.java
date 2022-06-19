package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientServiceImplTest {

    @Autowired
    ClientServiceImpl clientService;

    @Autowired
    ClientRepository clientRepository;

    @Test
    void addNewClient() {
        Account account = new Account();
        account.setAmount(100);
        account.setCurrency("RUB");
        Client client = new Client();
        client.setAge(20);
        client.setName("Vasily");
        client.getAccounts().add(account);
        Client res = clientService.addNewClient(client);
        assertEquals(1, res.getId());
    }

    @Test
    void findClientById() {
    }
}