package com.ecommerce.coupon.persistence;

import com.ecommerce.coupon.domain.Coupon;
import com.ecommerce.coupon.domain.CouponId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponId;
    private Long discountPercent;

    public Coupon toCoupon(){
        return Coupon.of(CouponId.from(String.valueOf(this.couponId)),String.valueOf(this.discountPercent));
    }

    public static CouponEntity from(final Coupon coupon){
        return new CouponEntity(coupon.getCouponId().getValue(), coupon.getDiscountPercent());
    }
}
