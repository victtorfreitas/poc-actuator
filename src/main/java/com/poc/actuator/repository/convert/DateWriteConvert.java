package com.poc.actuator.repository.convert;


import org.springframework.core.convert.converter.Converter;

import java.time.Instant;
import java.util.Date;

public class DateWriteConvert implements Converter<String, Date> {


    @Override
    public Date convert(String source) {
        return Date.from(Instant.parse(source));
    }
}
