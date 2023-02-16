package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.github.kalininaleksandrv.makejpagreateagain.model.converter.ZipCodeConverter;

import javax.persistence.*;

@Embeddable
public class BillingAddress {

    @Embedded
    @AttributeOverrides ({@AttributeOverride(name = "city", column = @Column(name = "CITY")),
    @AttributeOverride(name = "country", column = @Column(name = "COUNTRY"))})
    private City city;

    @Convert(converter = ZipCodeConverter.class,
            attributeName = "zipcode")
    private ZipCode zipcode;

    @Override
    public String toString() {
        return "BillingAddress{" +
                "city=" + city +
                ", zipcode=" + zipcode +
                '}';
    }
}
