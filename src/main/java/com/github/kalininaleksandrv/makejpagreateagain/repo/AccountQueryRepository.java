package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountQueryRepository extends JpaRepository<Account, Long> {

    /*
    JPQL query
    */
    @Query("select count(a) from Account a where a.currency = ?1")
    int findNumberOfAccountsByCurrency(Currency currency);

    /*
    JPQL query
    params in a query not depends on names, but only on order
    we use @Param for not depend on order of parameters in query
     */
    @Query("select a from Account a where a.currency = :currency and a.amount >= :amount")
    List<Account> findByAmountAndCurrency(@Param("amount") int amount, @Param("currency") Currency currency);

    /*
    native sql query
     */
    @Query(value = "SELECT COUNT(*) FROM ACCOUNT WHERE AMOUNT < ?1", nativeQuery = true)
    int findNumberOfAccountsLessThenAmount(int amount);
}
