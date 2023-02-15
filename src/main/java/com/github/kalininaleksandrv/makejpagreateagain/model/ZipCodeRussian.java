package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.*;

public class ZipCodeRussian extends ZipCode{
    @JsonCreator
    public ZipCodeRussian(String value) {
        super(value);
    }
}
