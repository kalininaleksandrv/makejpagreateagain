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
    // TODO: 07.02.2023 implement ZipCode abstract class with 2 impl - 5 and 6 digit, create custom converter
    @Column(length = 6)
    private String zipcode;
}
