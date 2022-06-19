package com.github.kalininaleksandrv.makejpagreateagain.repo;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
}
