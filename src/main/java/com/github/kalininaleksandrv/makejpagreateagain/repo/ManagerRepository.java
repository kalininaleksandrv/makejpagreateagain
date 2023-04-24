package com.github.kalininaleksandrv.makejpagreateagain.repo;


import com.github.kalininaleksandrv.makejpagreateagain.model.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = Manager.MANAGER_CLIENTS)
    @Override
    Optional<Manager> findById(Integer id);
}
