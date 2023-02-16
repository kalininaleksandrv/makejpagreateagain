package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NamedEntityGraph(name = Account.ACCOUNT_CLIENT_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode(value = "client")})
@Entity
public class Account {

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

    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
