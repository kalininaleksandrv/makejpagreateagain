package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.*;

public class ZipCodeEmpty extends ZipCode{
    @JsonCreator
    public ZipCodeEmpty(String value) {
        super(value);
    }
}
