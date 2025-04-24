package com.ecommerce.coupon.dto;

import com.ecommerce.coupon.domain.Coupon;
import com.ecommerce.coupon.domain.CouponId;
import com.ecommerce.coupon.domain.CouponProduct;
import com.ecommerce.coupon.domain.UserCoupon;
import com.ecommerce.grobal.util.NonDuplicatedList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InternalCouponValidateRequest {
    private final String memberId;
    private final List<CouponRequest> coupons;

    public NonDuplicatedList<UserCoupon> toUserCouponList(){
        final List<UserCoupon> userCoupons = coupons.stream()
                .map(i->{
                    final Coupon coupon = Coupon.of(CouponId.from(i.getCouponId()),
                            i.getCouponDiscountPercent(),
                            i.getOriginPrice(),
                            i.getDiscountedPrice());
                    final UserCoupon userCoupon = UserCoupon.of(i.getUserCouponId(),getMemberId(),coupon);
                    return userCoupon;
                })
                .toList();
        return new NonDuplicatedList<>(userCoupons);
    }
    public List<CouponProduct> toCouponProducts(){
        return coupons.stream().map(i->{
            final Coupon coupon = Coupon.of(CouponId.from(i.getCouponId()),i.getCouponDiscountPercent());
            return CouponProduct.of(coupon,i.getProductId(),i.getOriginPrice());
        }).toList();
    }

    public static InternalCouponValidateRequest from(final String memberId,final List<CouponRequest> coupons){
        return new InternalCouponValidateRequest(memberId, coupons);
    }
}
