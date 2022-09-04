package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class UserAndAccountBaseApplicationTests {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	AccountRepository accountRepository;

	@BeforeAll
	void beforeAll() {
		Client entity = generateClient();
		clientRepository.save(entity);
		accountRepository.saveAll(addAccountsToClient(entity));
	}

	private List<Account> addAccountsToClient(Client entity) {
		Account account = new Account();
		account.setAmount(100);
		account.setCurrency("RUB");
		Account account2 = new Account();
		account.setAmount(200);
		account.setCurrency("RUB");
		List<Account> accounts = new ArrayList<>();
		accounts.add(account);
		accounts.add(account2);
		accounts.forEach(i -> i.setClient(entity));
		return accounts;
	}

	private Client generateClient() {
		Client client = new Client();
		client.setAge(20);
		client.setName("First Client");
		return client;
	}

}
