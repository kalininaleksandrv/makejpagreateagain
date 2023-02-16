package com.github.kalininaleksandrv.makejpagreateagain.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, defaultImpl = ZipCodeEmpty.class, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ZipCodeEmpty.class, name = "undefined"),
        @JsonSubTypes.Type(value = ZipCodeRussian.class, name = "ZipCodeRussian"),
        @JsonSubTypes.Type(value = ZipCodeSpain.class, name = "ZipCodeSpain")
})
@AllArgsConstructor
@Getter
@Setter
public abstract class ZipCode {

    private String value;

}
