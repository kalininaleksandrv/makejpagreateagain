package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
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
    @Getter
    private Integer id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private int age;

    @Getter
    @Setter
    private BillingAddress billingAddress;

    // TODO: 26.06.2022 add business-key
    // TODO: 08.01.2023 add single and composite unique constrains

    @JsonIgnoreProperties("client")
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @Setter
    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setClient(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (age != client.age) return false;
        if (!Objects.equals(name, client.name)) return false;
        return Objects.equals(billingAddress, client.billingAddress);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
