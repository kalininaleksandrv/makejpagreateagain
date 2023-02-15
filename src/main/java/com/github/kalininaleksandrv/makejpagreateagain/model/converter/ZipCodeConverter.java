package com.github.kalininaleksandrv.makejpagreateagain.model.converter;

import com.github.kalininaleksandrv.makejpagreateagain.exception.AccountProcessingException;
import com.github.kalininaleksandrv.makejpagreateagain.model.*;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ZipCodeConverter implements AttributeConverter<ZipCode, String> {

    public static final String NO_ZIPCODE_PROVIDED = "no zipcode provided";

    @Override
    public String convertToDatabaseColumn(ZipCode zipCode) {
        if(zipCode==null) return NO_ZIPCODE_PROVIDED;
        return zipCode.getValue();
    }

    @Override
    public ZipCode convertToEntityAttribute(String s) {
        if(s == null || s.equals(NO_ZIPCODE_PROVIDED)) return new ZipCodeEmpty(NO_ZIPCODE_PROVIDED);
        if(s.length() == 6) return new ZipCodeRussian(s);
        if(s.length() == 5) return new ZipCodeSpain(s);
        throw new AccountProcessingException("ZipCode must contains 5 or 6 digits but passed " + s);
    }
}
