package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.Manager;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{

    private final ManagerRepository managerRepository;
    private final ClientServiceImpl clientService;

    @Override
    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    @Transactional
    public boolean setManagerToClient(Integer clientId, Integer managerId) {
        Optional<Manager> savedManager = managerRepository.findById(managerId);
        Optional<Client> clientById = clientService.findClientById(clientId);
        savedManager
                .orElseThrow(() -> new UnsupportedOperationException(
                        String.format("unable to assign manager - no manager with id %s ", managerId)))
                .addClient(clientById
                .orElseThrow(() -> new UnsupportedOperationException("unable to assign manager to unknown client")));
        return true;
    }

    @Override
    public Optional<Manager> findById(Integer id) {
        return managerRepository.findById(id);
    }
}
