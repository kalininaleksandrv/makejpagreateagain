package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NamedEntityGraph(name = ScoringRate.RATE_CLIENT_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode(value = "client")})
@Entity
@NoArgsConstructor
public class ScoringRate {

    public static final String RATE_CLIENT_ENTITY_GRAPH = "rate-client-entity-graph";

    @Id
    @Getter
    private Integer id;

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.ALL
    )
    @PrimaryKeyJoinColumn
    @Getter
    @Setter
    private Client client;

    @Getter
    @Setter
    private int rate;

    public ScoringRate(Client client, int scoringRate) {
        this.id = client.getId();
        this.rate = scoringRate;
        this.client = client;
        client.setScoringRate(this);
    }
}
