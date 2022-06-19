package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.exception.AccountProcessingException;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client addNewClient(Client client) {
        if(client.getId() != null){
            Optional<Client> byId = clientRepository.findById(client.getId());
            if(byId.isPresent()){
                throw new AccountProcessingException("if client already exist client it must be modifying");
            }
            client.setId(null);
        }
        return clientRepository.save(client);
    }

    @Override
    public Client findClientById(Integer id) {
        return null;
    }
}
