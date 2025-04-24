package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.OrderException;
import com.ecommerce.order.exception.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Price {
    private static final Long MINIMUM_ORDER_PRICE = 0L;
    private final Long value;

    private Price(final String value){
        this.value = validate(value);
    }

    private Long validate(final String value){
        try {
            final Long longValue = Long.valueOf(value);
            if(longValue<MINIMUM_ORDER_PRICE){
                throw new FailedCreationException("가격은 음수일수 없습니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("올바른 가격을 입력해주세요.");
        }
    }

    public static Price from(final String value){
        return new Price(value);
    }
}
