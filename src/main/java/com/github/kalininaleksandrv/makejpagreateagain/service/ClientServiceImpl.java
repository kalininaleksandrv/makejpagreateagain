package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;

    @Override
    public Client saveClient(Client client) {
        Client savedClient = clientRepository.save(client);
        List<Account> acs = new ArrayList<>(savedClient.getAccounts());
        for (Account a: acs) {
            a.setClient(client);
        }
        accountRepository.saveAll(acs);
        return savedClient;
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
