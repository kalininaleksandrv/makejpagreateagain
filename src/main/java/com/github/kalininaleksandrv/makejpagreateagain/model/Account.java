package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@NamedEntityGraph(name = Account.ACCOUNT_CLIENT_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode(value = "client")})
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Account {

    public static final String ACCOUNT_CLIENT_ENTITY_GRAPH = "account-client-entity-graph";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Integer id;

    private int amount; // TODO: 19.06.2022 to BigDecimal
    private String currency; // TODO: 19.06.2022 to Enum

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    @JsonIgnoreProperties("accounts")
    @EqualsAndHashCode.Exclude
    private Client client;
}
