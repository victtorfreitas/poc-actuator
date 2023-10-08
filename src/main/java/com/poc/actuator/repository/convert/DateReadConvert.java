package com.poc.actuator.repository.convert;

import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class DateReadConvert implements Converter<Date, String> {
    @Override
    public String convert(Date date) {
        return date.toString();
    }
}
