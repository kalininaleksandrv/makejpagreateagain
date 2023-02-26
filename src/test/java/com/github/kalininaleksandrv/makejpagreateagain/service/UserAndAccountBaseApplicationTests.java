package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.*;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class UserAndAccountBaseApplicationTests {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	AccountRepository accountRepository;

	/*
	we can also use TestEntityManager, runs inside TransactionTemplate lambda to fill DB and JDBC Template to clear tables
	to @Autowired this classes use @AutoConfigureTestEntityManager which is a part of @DataJpaTest
	when using @DataJpaTest do not forget switch off @Transaction behavior
	 */
	@BeforeEach
	void setup() {
		accountRepository.deleteAll();
		clientRepository.deleteAll();
		Client entity = generateClient();
		clientRepository.save(entity);
		accountRepository.saveAll(addAccountsToClient(entity));
	}

	private List<Account> addAccountsToClient(Client entity) {

		List<Account> accounts = new ArrayList<>();
		for (int i = 1; i<11; i++){
            Account account = new Account();
            account.setAmount(BigDecimal.valueOf(i * 100));
            if (i % 2 != 0) {
                account.setCurrency(Currency.EUR);
            } else {
                account.setCurrency(Currency.USD);
            }
            if (i % 3 == 0) {
                account.setBlocked(true);
                account.setBlockingReason("fraud");
            }
            account.setClient(entity);
            accounts.add(account);
        }
		return accounts;
	}

	private Client generateClient() {
		Client client = new Client();
		client.setAge(20);
		client.setName("First Client");
		if(new Random().nextInt(3)%2==0){
			client.setContact(new ContactInfoEmail("email@mail.com"));
		} else {
			client.setContact(new ContactInfoPhone("+74951111111"));
		}
		return client;
	}

}
