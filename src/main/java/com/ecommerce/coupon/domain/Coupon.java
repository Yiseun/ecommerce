package com.ecommerce.coupon.domain;

import com.ecommerce.coupon.exception.domain.BusinessLogicException;
import com.ecommerce.coupon.exception.domain.FailedCreationException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Coupon {
    private static final Long MAXIMUM_DISCOUNT_PERCENT = 100L;
    private static final Long MINIMUM_DISCOUNT_PERCENT = 1L;
    private final CouponId couponId;
    private final Long discountPercent;


    private Coupon(final CouponId couponId,final String discountPercent){
        this.couponId = couponId;
        this.discountPercent = validateDiscountPercent(discountPercent);
    }


    private Long validateDiscountPercent(final String discountPercent){
        try {
            final Long longValue = Long.valueOf(discountPercent);
            if(longValue<MINIMUM_DISCOUNT_PERCENT){
                throw new FailedCreationException("최소 쿠폰 할인 비율은 "+MINIMUM_DISCOUNT_PERCENT+"입니다.");
            }
            if(longValue>MAXIMUM_DISCOUNT_PERCENT){
                throw new FailedCreationException("최대 쿠폰 할인 비율은 "+MAXIMUM_DISCOUNT_PERCENT+"입니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("discountPercent의 올바른형식을 입력해주세요.");
        }
    }

    public Long calculateDiscountPrice(final String originPrice){
        try{
            final long longValue = Long.parseLong(originPrice);
            final long minimumPrice = 1L;
            if(longValue<minimumPrice){
                throw new BusinessLogicException("쿠폰은 0원을 넘는 가격일때만 사용할수있습니다");
            }
            final long maxPercentValue = 100L;
            final long overflowPredictValue = Long.MAX_VALUE/(maxPercentValue-MINIMUM_DISCOUNT_PERCENT);
            if(longValue>overflowPredictValue){
                throw new BusinessLogicException("아직 이렇게 큰 수는 지원하지 않습니다.");
            }
            return longValue*(maxPercentValue-discountPercent)/maxPercentValue;
        }catch (NumberFormatException e){
            throw new BusinessLogicException("올바른 가격을 입력해주세요.");
        }
    }

    public static Coupon of(final CouponId couponId,final String discountPercent){
        return new Coupon(couponId,discountPercent);
    }

    public static Coupon of(final CouponId couponId,final String discountPercent,final String originPrice,final String discountPrice){
        final Coupon coupon = new Coupon(couponId,discountPercent);
        final Long realPrice = coupon.calculateDiscountPrice(originPrice);
        if(!discountPrice.equals(String.valueOf(realPrice))){
            throw new FailedCreationException("쿠폰 정보가 다릅니다.");
        }
        return coupon;
    }
}
