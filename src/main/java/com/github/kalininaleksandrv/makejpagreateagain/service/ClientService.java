package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;

public interface ClientService {

    Client addNewClient(Client client);
    Client findClientById(Integer id);
}
