package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedEntityGraph(name = Manager.MANAGER_CLIENTS,
        attributeNodes = {
                @NamedAttributeNode(value = "clients", subgraph = Manager.MANAGER_AND_CLIENTS)
        })
public class Manager {

    public static final String MANAGER_CLIENTS = "manager-clients";
    public static final String MANAGER_AND_CLIENTS = "manager-and-clients";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @Getter
    private Integer id;

    @Getter
    @Setter
    private int personnelNumber;

    @Setter
    @OneToMany(mappedBy = "manager", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"manager", "rate", "accounts", "contact"})
    private Set<Client> clients = new HashSet<>();

    public Set<Client> getClients() {
        return Collections.unmodifiableSet(clients);
    }

    public void addClient(Client client) {
        clients.add(client);
        client.setManager(this);
    }
}
