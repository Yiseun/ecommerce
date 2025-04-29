package com.ecommerce.coupon.dto;

import com.ecommerce.coupon.domain.Coupon;
import com.ecommerce.coupon.domain.CouponId;
import com.ecommerce.coupon.domain.CouponProduct;
import com.ecommerce.coupon.domain.UserCoupon;
import com.ecommerce.grobal.util.NonDuplicatedList;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class InternalCouponUseRequest {
    private final String memberId;
    private final List<CouponRequest> coupons;

    public NonDuplicatedList<UserCoupon> toUserCoupons(){
        final List<UserCoupon> userCoupons = this.coupons.stream().map(i->{
            final Coupon coupon = Coupon.of(CouponId.from(i.getCouponId()),
                    i.getCouponDiscountPercent(),
                    i.getOriginPrice(),
                    i.getDiscountedPrice());
            return UserCoupon.of(i.getUserCouponId(),this.memberId,coupon);
        }).toList();
        return new NonDuplicatedList<>(userCoupons);
    }
    public List<CouponProduct> toCouponProducts(){
        return coupons.stream().map(i->{
            final Coupon coupon = Coupon.of(CouponId.from(i.getCouponId()),i.getCouponDiscountPercent());
            return CouponProduct.of(coupon,i.getProductId(),i.getOriginPrice());
        }).toList();
    }
    public static InternalCouponUseRequest of(final String memberId,final List<CouponRequest> coupons){
        return new InternalCouponUseRequest(memberId, coupons);
    }
}
