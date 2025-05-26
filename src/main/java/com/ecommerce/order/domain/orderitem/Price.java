package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Price {
    private static final Long MINIMUM_ORDER_PRICE = 0L;
    private final Long value;

    private Price(){
        this.value = null;
    }
    private Price(final String value){
        this.value = parse(value);
    }

    private Long parse(final String value){
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
    public static Price createEmpty(){
        return new Price();
    }
}
