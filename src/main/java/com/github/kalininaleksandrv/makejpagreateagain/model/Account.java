package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@NamedEntityGraph(name = "account-client-entity-graph",
        attributeNodes = {@NamedAttributeNode(value = "client")})
@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    @EqualsAndHashCode.Exclude
    private Integer id;

    private int amount; // TODO: 19.06.2022 to BigDecimal
    private String currency; // TODO: 19.06.2022 to Enum

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_client")
    @JsonIgnoreProperties("accounts")
    private Client client;
}
