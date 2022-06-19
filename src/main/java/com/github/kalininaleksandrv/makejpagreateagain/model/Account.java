package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_account")
    private Integer id;

    private int amount; // TODO: 19.06.2022 to BigDecimal
    private String currency; // TODO: 19.06.2022 to Enum

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;
}
