package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@EqualsAndHashCode
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_client")
    @EqualsAndHashCode.Exclude
    private Integer id;

    private String name;
    private int age;

    // TODO: 26.06.2022 add business-key
    // TODO: 05.08.2022 add custom getter and helper methods

    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("client")
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setClient(this);
    }

}
