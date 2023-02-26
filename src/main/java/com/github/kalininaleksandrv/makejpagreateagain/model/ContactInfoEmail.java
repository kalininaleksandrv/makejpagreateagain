package com.github.kalininaleksandrv.makejpagreateagain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
public class ContactInfoEmail extends ContactInfo{

    @Getter
    @Setter
    private String email;

    public ContactInfoEmail() {
    }

    public ContactInfoEmail(String email) {
        this.email = email;
    }
}
