package com.example.myapplication.data.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

public class IsFavouriteConverter implements Converter<Boolean, Boolean> {

    @Override
    public Boolean convert(MappingContext<Boolean, Boolean> context) {
        return true;
    }
}
