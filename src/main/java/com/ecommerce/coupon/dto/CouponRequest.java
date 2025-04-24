package com.ecommerce.coupon.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CouponRequest {
    private final String userCouponId;
    private final String couponId;
    private final String productId;
    private final String originPrice;
    private final String discountedPrice;
    private final String couponDiscountPercent;
}
