package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    @EqualsAndHashCode.Exclude
    private Integer id;

    private String name;
    private int age;

    // TODO: 26.06.2022 add business-key

    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("client")
    @OneToMany(mappedBy = "client", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>();
}
