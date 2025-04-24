package com.ecommerce.order.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderItemDto {
    private final String productId;
    private final String productName;
    private final String quantity;
    private final String price;
    private final String discountPrice;
    private final String couponDiscountPercent;
    private final String couponId;
    private final String userCouponId;
    private final String orderItemState;
    private final String trackingInfo;
}
