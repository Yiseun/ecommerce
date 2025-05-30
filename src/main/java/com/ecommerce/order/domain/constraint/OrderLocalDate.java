package com.ecommerce.order.domain.constraint;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Getter
public class OrderLocalDate {
    private final LocalDate localDate;

    private OrderLocalDate(final String value){
        this.localDate = parse(value);
    }

    private LocalDate parse(final String value){
        try{
            return LocalDate.parse(value);
        }catch (DateTimeParseException e){
            throw new FailedCreationException("날짜는 "+value+"일수 없습니다.");
        }
    }
    public static OrderLocalDate from(final String value){
        return new OrderLocalDate(value);
    }
}
