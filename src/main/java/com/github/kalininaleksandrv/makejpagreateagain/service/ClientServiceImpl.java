package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

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

}
