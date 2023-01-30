package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class City {
    private String country;
    private String city;
}
