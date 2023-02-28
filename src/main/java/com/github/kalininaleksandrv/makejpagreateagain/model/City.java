package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
public class City {

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String city;

    @Override
    public String toString() {
        return "City{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
