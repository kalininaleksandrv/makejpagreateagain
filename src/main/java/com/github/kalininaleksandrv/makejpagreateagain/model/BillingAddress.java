package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.github.kalininaleksandrv.makejpagreateagain.model.converter.ZipCodeConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
public class BillingAddress {

    @Embedded
    @AttributeOverrides (
            {@AttributeOverride(name = "city", column = @Column(name = "CITY")),
                    @AttributeOverride(name = "country", column = @Column(name = "COUNTRY"))})
    @Getter
    @Setter
    private City city;

    @Convert(converter = ZipCodeConverter.class,
            attributeName = "zipcode")
    @Getter
    @Setter
    private ZipCode zipcode;

    @Override
    public String toString() {
        return "BillingAddress{" +
                "city=" + city +
                ", zipcode=" + zipcode +
                '}';
    }
}
