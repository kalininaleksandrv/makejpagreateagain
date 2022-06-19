package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;

import java.util.Optional;

public interface ClientService {

    Client saveClient(Client client);
    Optional<Client> findClientById(Integer id);

    Iterable<Client> findAll();
}
