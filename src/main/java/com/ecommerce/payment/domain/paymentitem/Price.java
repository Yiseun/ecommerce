package com.ecommerce.payment.domain.paymentitem;

import com.ecommerce.payment.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Price {
    private static final Long MINIMUM_PURCHASE_PRICE = 0L;
    private final Long value;
    private Price(final String value){
        this.value = validate(value);
    }
    private Long validate(final String value){
        try {
            final long longValue = Long.parseLong(value);
            if(longValue<MINIMUM_PURCHASE_PRICE){
                throw new FailedCreationException("구매가격이 0원보다 작을수는 없습니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("가격 형식이 잘못됐습니다.");
        }
    }
    public static Price from(final String value){
        return new Price(value);
    }
}
