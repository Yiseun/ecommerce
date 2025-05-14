package com.ecommerce.grobal;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class Time {
    public Date getDate(){
        final ZonedDateTime zonedDateTime = ZonedDateTime.now();
        final Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }
}
