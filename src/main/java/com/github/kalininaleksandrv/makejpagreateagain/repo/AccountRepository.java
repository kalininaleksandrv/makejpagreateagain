package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {

    //this is named entity graph fore avoiding n+1 problem, name defines in entity
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = "account-client-entity-graph")
    @Override
    Iterable<Account> findAll();
}
