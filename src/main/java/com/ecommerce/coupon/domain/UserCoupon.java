package com.ecommerce.coupon.domain;

import com.ecommerce.coupon.exception.domain.FailedCreationException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCoupon {

    private final Long userCouponId;
    private final String memberId;
    private final Coupon coupon;

    private UserCoupon(final String userCouponId,final String memberId, final Coupon coupon){
        this.userCouponId = validateUserCouponId(userCouponId);
        this.memberId = memberId;
        this.coupon = coupon;
    }

    private Long validateUserCouponId(final String userCouponId){
        try{
            final long minimumLongValue = 0L;
            final long longValue = Long.parseLong(userCouponId);
            if(longValue<minimumLongValue){
                throw new FailedCreationException("Id형식이 올바르지 않습니다.");
            }
            return longValue;
        }catch (NumberFormatException e){
            throw new FailedCreationException("id형식이 올바르지 않습니다.");
        }
    }

    public static UserCoupon of(final String userCouponId,final String memberId,final Coupon coupon){
        return new UserCoupon(userCouponId, memberId, coupon);
    }
}
