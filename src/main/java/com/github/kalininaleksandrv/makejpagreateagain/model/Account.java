package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
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

    // TODO: 08.01.2023 add single and composite unique constrains

    public static final String ACCOUNT_CLIENT_ENTITY_GRAPH = "account-client-entity-graph";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_gen")
    @SequenceGenerator(allocationSize = 1, name="account_seq_gen", sequenceName="account_id_seq")
    @Column(name = "id_account")
    @EqualsAndHashCode.Exclude
    @Setter(AccessLevel.NONE)
    private Integer id;

    @Column(nullable= false, precision=12, scale=2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;
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
