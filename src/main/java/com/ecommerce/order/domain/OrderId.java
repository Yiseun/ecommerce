package com.ecommerce.order.domain;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OrderId {
    private static final long MINIMUM_ORDERID_VALUE = 0;
    private final Long value;
    private OrderId(final String value){
        this.value = validate(value);
    }

    private Long validate(String value){
        if(value==null){
            return null;
        }
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

    public static OrderId from(final String value){
        if(value==null){
            throw new FailedCreationException("OrderId는 필수입니다.");
        }
        return new OrderId(value);
    }
    public static OrderId createEmpty(){
        return new OrderId(null);
    }
}
