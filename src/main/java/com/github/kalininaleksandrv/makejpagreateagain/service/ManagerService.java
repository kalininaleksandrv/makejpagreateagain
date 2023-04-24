package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Manager;

import java.util.Optional;

public interface ManagerService {
    Manager saveManager(Manager manager);
    boolean setManagerToClient (Integer managerId, Integer clientId);

    Optional<Manager> findById(Integer id);
}
