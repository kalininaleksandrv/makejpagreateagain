package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class BillingAddress {

    private String address;
    @Column(length = 6)
    private String zipcode;
}
