package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Integer> {
    Client findByName(String name);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = Client.CLIENT_ACCOUNT_CONTACT_ENTITY_GRAPH)
    @Override
    Iterable<Client> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = Client.CLIENT_ACCOUNT_CONTACT_ENTITY_GRAPH)
    @Override
    Optional<Client> findById(Integer id);
}
