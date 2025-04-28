package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class OrderItemId {
    private static final Long MINIMUM_ORDERITEM_VALUE = 0L;
    private final Long value;

    private OrderItemId(final String value){
        this.value = validate(value);
    }

    private Long validate(final String value){
        if(value==null){
            return null;
        }
        try{
            final Long longValue = Long.valueOf(value);
            if(longValue<MINIMUM_ORDERITEM_VALUE){
                throw new FailedCreationException("orderItemId는 음수일수없습니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException(value+"는 OrderItemId의 형식이 아닙니다.");
        }
    }

    public static OrderItemId from(final String value){
        if(value==null){
            throw new FailedCreationException("OrderItemId는 필수입력 값입니다.");
        }
        return new OrderItemId(value);
    }

    public static OrderItemId createEmpty(){
        return new OrderItemId(null);
    }

}
