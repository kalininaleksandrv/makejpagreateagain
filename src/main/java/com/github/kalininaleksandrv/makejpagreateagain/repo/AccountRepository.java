package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    //this is named entity graph fore avoiding n+1 problem, name defines in entity
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = Account.ACCOUNT_CLIENT_ENTITY_GRAPH)
    @Override
    List<Account> findAll();

}
