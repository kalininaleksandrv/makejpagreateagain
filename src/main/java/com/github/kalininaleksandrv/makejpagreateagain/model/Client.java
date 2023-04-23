package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedEntityGraph(name = Client.CLIENT_ACCOUNT_CONTACT_SCORING_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode(value = "contact", subgraph = Client.CLIENT_AND_CONTACT),
                @NamedAttributeNode(value = "accounts", subgraph = Client.CLIENT_AND_ACCOUNTS),
                @NamedAttributeNode(value = "rate", subgraph = Client.CLIENT_AND_SCORING)
        })
public class Client {

    public static final String CLIENT_ACCOUNT_CONTACT_SCORING_ENTITY_GRAPH = "client-account-contact-entity-graph";
    public static final String CLIENT_AND_CONTACT = "client-and-contact";
    public static final String CLIENT_AND_ACCOUNTS = "client-and-accounts";
    public static final String CLIENT_AND_SCORING = "client-and-scoring";

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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ContactInfo contact;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    /*
    we can have Client without scoring rate but not - scoring rate without Client
    that's why CLIENT_ID is a PK - until we don't add ScoringRate to Client, the row in intermediate table
    not have been created
    but IF we create the row it must contain RATE_ID, that's why nullable is false
    so, we save Client, and then we create and save ScoringRate with this Client
     */
    @JoinTable(
            name = "CLIENT_SCORE",
            joinColumns = @JoinColumn(name = "CLIENT_ID", unique = true, nullable = false),
            inverseJoinColumns = @JoinColumn(name = "RATE_ID")
    )
    @JsonIgnoreProperties("client")
    private ScoringRate rate;

    @Getter
    @Setter
    private BillingAddress billingAddress;

    // TODO: 26.06.2022 add business-key
    // TODO: 08.01.2023 add single and composite unique constrains

    /*
    we change list to collection because we don't need to save explicit order of stored elements in DB
    for store elements in preserved order we can use list but in this case we should make the owing side of
    relationship (many-to-one) read only (see pt. 9.5.3. Java Persistent in Action)
    preserve order in a distinct column is rarely needed and can affect performance badly
     */
    @JsonIgnoreProperties("client")
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @Setter
    private Collection<Account> accounts = new ArrayList<>();

    public Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(accounts);
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
