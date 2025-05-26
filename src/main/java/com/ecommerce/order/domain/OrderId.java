package com.ecommerce.order.domain;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderId {
    private static final long MINIMUM_ORDERID_VALUE = 0;
    private final Long value;

    private OrderId(){
        this.value = null;
    }
    private OrderId(final String value){
        this.value = parse(value);
    }

    private Long parse(final String value){
        try {
            final long longTypeValue = Long.parseLong(value);
            if(longTypeValue<MINIMUM_ORDERID_VALUE){
                throw new FailedCreationException(value + "로 OrderId를 만들 수 없습니다.");
            }
            return longTypeValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException(value + "로 OrderId를 만들 수 없습니다.");
        }
    }

    public boolean isEmpty(){
        return value == null;
    }

    public static OrderId from(final String value){
        return new OrderId(value);
    }
    public static OrderId createEmpty(){
        return new OrderId();
    }
}
