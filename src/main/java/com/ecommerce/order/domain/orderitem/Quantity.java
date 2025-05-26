package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Quantity {
    private static final Long MINIMUM_ORDER_QUANTITY = 1L;
    private final Long value;
    private Quantity(final String value){
        this.value = parse(value);
    }

    private Long parse(final String value){
        try{
            final Long longValue = Long.valueOf(value);
            if(longValue<MINIMUM_ORDER_QUANTITY){
                throw new FailedCreationException("구매수량은 1개이상이어야합니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("올바른 수량을 입력해주세요.");
        }
    }

    public static Quantity from(final String value){
        return new Quantity(value);
    }
}
