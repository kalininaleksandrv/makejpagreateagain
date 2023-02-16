package com.github.kalininaleksandrv.makejpagreateagain.model;

import javax.persistence.Embeddable;

@Embeddable
public class City {
    private String country;
    private String city;

    @Override
    public String toString() {
        return "City{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
