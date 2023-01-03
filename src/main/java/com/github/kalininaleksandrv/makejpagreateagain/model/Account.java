package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NamedEntityGraph(name = Account.ACCOUNT_CLIENT_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode(value = "client")})
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Account {

    public static final String ACCOUNT_CLIENT_ENTITY_GRAPH = "account-client-entity-graph";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="account_id_seq")
    @Column(name = "id_account")
    @EqualsAndHashCode.Exclude
    private Integer id;

    @Column(nullable= false, precision=12, scale=2)
    private BigDecimal amount;
    private Currency currency; // TODO: 03.01.2023 use ENUM annotation
    private boolean blocked;
    private String blockingReason;
    // TODO: 26.06.2022 add business-key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    @JsonIgnoreProperties("accounts")
    @EqualsAndHashCode.Exclude
    private Client client;

    public void addClient(Client client) {
        this.client = client;
        client.addAccount(this);
    }
}
