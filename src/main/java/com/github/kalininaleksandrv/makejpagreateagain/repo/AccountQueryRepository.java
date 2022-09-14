package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountQueryRepository extends JpaRepository<Account, Long> {

    @Query("select count(a) from Account a where a.currency = ?1")
    int findNumberOfAccountsByCurrency(Currency currency);
}
