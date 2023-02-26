package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class ContactInfoPhone extends ContactInfo {
    @Getter
    @Setter
    private String number;

    public ContactInfoPhone() {
    }

    public ContactInfoPhone(String number) {
        this.number = number;
    }
}
