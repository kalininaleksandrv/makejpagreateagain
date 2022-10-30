package com.github.kalininaleksandrv.makejpagreateagain.service;

import com.github.kalininaleksandrv.makejpagreateagain.model.Account;
import com.github.kalininaleksandrv.makejpagreateagain.model.Client;
import com.github.kalininaleksandrv.makejpagreateagain.model.Currency;
import com.github.kalininaleksandrv.makejpagreateagain.repo.AccountRepository;
import com.github.kalininaleksandrv.makejpagreateagain.repo.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class UserAndAccountBaseApplicationTests {

	@Autowired
	ClientRepository clientRepository;
	@Autowired
	AccountRepository accountRepository;

	@BeforeEach
	void setup() {
		accountRepository.deleteAll();
		clientRepository.deleteAll();
		Client entity = generateClient();
		clientRepository.save(entity);
		// TODO: 04.09.2022 batch insert in DB
		accountRepository.saveAll(addAccountsToClient(entity));
	}

	private List<Account> addAccountsToClient(Client entity) {

		List<Account> accounts = new ArrayList<>();
		for (int i = 1; i<11; i++){
			Account account = new Account();
			account.setAmount(i*100);
			if(i%2!=0){
				account.setCurrency(Currency.EUR);
			} else {
				account.setCurrency(Currency.USD);
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
		return client;
	}

}
