package com.ecommerce.coupon.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class CouponProduct {

    @EqualsAndHashCode.Include
    private final Coupon coupon;
    @EqualsAndHashCode.Include
    private final String productId;
    private final String productPrice;

    private CouponProduct(final Coupon coupon,final String productId,final String productPrice){
        this.coupon = coupon;
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public static CouponProduct of(final Coupon coupon,final String productId,final String productPrice){
        return new CouponProduct(coupon, productId, productPrice);
    }
}
