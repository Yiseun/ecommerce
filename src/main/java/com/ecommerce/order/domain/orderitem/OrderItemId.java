package com.ecommerce.order.domain.orderitem;

import com.ecommerce.order.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class OrderItemId {
    private static final Long MINIMUM_ORDERITEM_VALUE = 0L;
    private final Long value;

    private OrderItemId(){
        this.value = null;
    }
    private OrderItemId(final String value){
        this.value = parse(value);
    }

    private Long parse(final String value){
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
        return new OrderItemId(value);
    }

    public static OrderItemId createEmpty(){
        return new OrderItemId();
    }

}
