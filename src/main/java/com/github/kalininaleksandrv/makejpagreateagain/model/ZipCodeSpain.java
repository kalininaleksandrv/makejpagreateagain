package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.*;

public class ZipCodeSpain extends ZipCode {
    @JsonCreator
    public ZipCodeSpain(String value) {
        super(value);
    }
}
