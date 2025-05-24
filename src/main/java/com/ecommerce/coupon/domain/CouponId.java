package com.ecommerce.coupon.domain;

import com.ecommerce.coupon.exception.application.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class CouponId {
    private static final Long MINIMUM_ID_VALUE = 0L;
    private final Long value;

    private CouponId(final String value){
        this.value = validateValue(value);
    }

    private Long validateValue(final String value){
        try {
            final Long longValue = Long.valueOf(value);
            if(longValue<MINIMUM_ID_VALUE){
                throw new FailedCreationException("Id가 유효하지 않습니다. id값은"+value);
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("Id가 유효하지 않습니다. id값은"+value);
        }
    }

    public static CouponId from(final String value){
        return new CouponId(value);
    }

    public static CouponId createEmptyId(){
        return new CouponId(null);
    }
}
