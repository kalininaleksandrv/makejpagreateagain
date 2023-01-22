package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
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
    /*
    GenerationType.SEQUENCE means that Hibernate or DB creates
    special object to generate IDs
    if DB NOT support SEQUENCE (MySQL) that we ends up
    with GenerationType.TABLE with awful performance
    GenerationType.IDENTITY means we use special column fo IDs
    a lot of DBs support this ability (Oracle only emulates this)

    GenerationType.SEQUENCE supports batching on JDBC,
    and GenerationType.IDENTITY - not
    */
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq_gen")
    @SequenceGenerator(allocationSize = 1, name="client_seq_gen", sequenceName="client_id_seq")
    @Column(name = "id_client")
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    private Integer id;

    private String name;
    private int age;

    // TODO: 26.06.2022 add business-key
    // TODO: 05.08.2022 add custom getter and helper methods
    // TODO: 08.01.2023 add single and composite unique constrains

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
