package com.ecommerce.payment.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PurchaseItemDto {
    private final String productId;
    private final String productName;
    private final String quantity;
    private final String price;
    private final String discountPrice;
    private final String couponDiscountPercent;
    private final String couponId;
    private final String userCouponId;
}
