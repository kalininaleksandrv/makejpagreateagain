package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {

        for (Account a: client.getAccounts()) {
            a.setClient(client);
        }
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findClientById(Integer id) {
        return clientRepository.findById(id);
    }

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Transactional
    @Override
    public Client saveClientWithAge(Client client, int age) {
        for (Account a: client.getAccounts()) {
            a.setClient(client);
        }
        Client savedClient = clientRepository.save(client);
        /*
        since there is @Transactional annotation above method, data of client will be
        committed only in the end of method, so age will be change do int age parameter
        if we remove @Transaction, setter after save won't be executed
         */
        savedClient.setAge(age);
        return savedClient;
    }

}
