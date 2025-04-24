package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.OrderException;
import com.ecommerce.order.exception.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Quantity {
    private static final Long MINIMUM_ORDER_QUANTITY = 0L;
    private final Long value;

    private Quantity(final String value){
        this.value = validate(value);
    }

    private Long validate(final String value){
        try{
            final Long longValue = Long.valueOf(value);
            if(longValue<MINIMUM_ORDER_QUANTITY){
                throw new FailedCreationException("수량은 음수일수 없습니다.");
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
