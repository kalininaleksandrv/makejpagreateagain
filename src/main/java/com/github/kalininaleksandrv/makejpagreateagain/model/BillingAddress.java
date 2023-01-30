package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable
public class BillingAddress {

    @Embedded
    @AttributeOverrides ({@AttributeOverride(name = "city", column = @Column(name = "CITY")),
    @AttributeOverride(name = "country", column = @Column(name = "COUNTRY"))})
    private City city;
    @Column(length = 6)
    private String zipcode;
}
