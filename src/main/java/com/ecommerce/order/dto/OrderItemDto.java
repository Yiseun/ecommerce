package com.ecommerce.order.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class OrderItemDto {
    private final String orderItemId;
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
