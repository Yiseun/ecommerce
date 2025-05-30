package com.ecommerce.order.domain.constraint;

import com.ecommerce.order.domain.OrderBase;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Constraint {

    private final OrderBase orderBase;
    private final DateRange dateRange;
    private final String size;

    public static Constraint of(final OrderBase orderBase,final DateRange dateRange,final String size){
        return new Constraint(orderBase,dateRange,size);
    }
}
