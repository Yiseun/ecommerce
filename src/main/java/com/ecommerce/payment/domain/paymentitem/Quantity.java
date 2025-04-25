package com.ecommerce.payment.domain.paymentitem;

import com.ecommerce.payment.exception.domain.FailedCreationException;
import lombok.Getter;

@Getter
public class Quantity {
    private static final Long MINIMUM_PURCHASE_QUANTITY = 1L;
    private final Long value;

    private Quantity(final String value){
        this.value = validate(value);
    }
    private Long validate(final String value){
        try{
            final long longValue = Long.parseLong(value);
            if(longValue<MINIMUM_PURCHASE_QUANTITY){
                throw new FailedCreationException("구매수량은 0개이상이어야 합니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("수량 형식이 잘못됐습니다.");
        }
    }
    public static Quantity from(final String value){
        return new Quantity(value);
    }
}
