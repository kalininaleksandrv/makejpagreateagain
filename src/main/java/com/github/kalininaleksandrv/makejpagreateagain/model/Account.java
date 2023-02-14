package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@NamedEntityGraph(name = Account.ACCOUNT_CLIENT_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode(value = "client")})
@Entity
public class Account {

    // TODO: 08.01.2023 add single and composite unique constrains

    public static final String ACCOUNT_CLIENT_ENTITY_GRAPH = "account-client-entity-graph";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_gen")
    @SequenceGenerator(allocationSize = 1, name="account_seq_gen", sequenceName="account_id_seq")
    @Column(name = "id_account")
    @Getter
    private Integer id;

    @Column(nullable= false, precision=12, scale=2)
    @Getter
    @Setter
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Currency currency;

    @Getter
    @Setter
    private boolean blocked;

    @Getter
    @Setter
    private String blockingReason;
    // TODO: 26.06.2022 add business-key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    @JsonIgnoreProperties("accounts")
    @Getter
    @Setter
    private Client client;

    public void addClient(Client client) {
        this.client = client;
        client.addAccount(this);
    }

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    @Getter
    @Setter
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    @Getter
    @Setter
    private LocalDateTime updated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (blocked != account.blocked) return false;
        if (!Objects.equals(amount, account.amount)) return false;
        if (currency != account.currency) return false;
        if (!Objects.equals(blockingReason, account.blockingReason))
            return false;
        if (!created.equals(account.created)) return false;
        return updated.equals(account.updated);
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (blocked ? 1 : 0);
        result = 31 * result + (blockingReason != null ? blockingReason.hashCode() : 0);
        result = 31 * result + created.hashCode();
        result = 31 * result + updated.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency=" + currency +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
