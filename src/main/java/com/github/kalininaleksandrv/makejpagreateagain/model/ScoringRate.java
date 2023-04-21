package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NamedEntityGraph(name = ScoringRate.RATE_CLIENT_ENTITY_GRAPH,
        attributeNodes = {@NamedAttributeNode(value = "client", subgraph = "client-subgraph")},
        subgraphs = {
                @NamedSubgraph(name = "client-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("accounts"),
                                @NamedAttributeNode("contact")
                        }
                )
        })
@Entity
@NoArgsConstructor
public class ScoringRate {

    public static final String RATE_CLIENT_ENTITY_GRAPH = "rate-client-entity-graph";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scoring_seq_gen")
    @SequenceGenerator(allocationSize = 1, name="scoring_seq_gen", sequenceName="scoring_id_seq")
    @Column(name = "id_scoring")
    @Getter
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "CLIENT_SCORE",
            joinColumns = @JoinColumn(name = "RATE_ID", nullable = false, unique = true),
            inverseJoinColumns = @JoinColumn(name = "CLIENT_ID")
    )
    @Getter
    @Setter
    private Client client;

    @Getter
    @Setter
    private int rate;

    public ScoringRate(Client client, int scoringRate) {
        this.rate = scoringRate;
        this.client = client;
        client.setRate(this);
    }
}
