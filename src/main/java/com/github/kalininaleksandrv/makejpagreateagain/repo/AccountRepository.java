package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    //this is named entity graph fore avoiding n+1 problem, name defines in entity
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = Account.ACCOUNT_CLIENT_ENTITY_GRAPH)
    @Override
    List<Account> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = Account.ACCOUNT_CLIENT_ENTITY_GRAPH)
    @Override
    Optional<Account> findById(Integer integer);

    /*
        simple method to work with projection - List<AccountView> not very convenient because of typification,
        so we turn it to List<T> and add Class<T> parameter
         */
    <T> List<T> findByBlockedTrue(Class<T> type);

    /*
    we need overloading here to one method works with entity graph and second - does not
     */
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,
            value = Account.ACCOUNT_CLIENT_ENTITY_GRAPH)
    <T> List<T> findByBlockedTrue();
}
