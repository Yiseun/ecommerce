package com.ecommerce.order.domain.orderitem;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class OrderItemInfo {
    @EqualsAndHashCode.Include
    private final OrderItemId orderItemId;
    private final String productId;
    private final String productName;
    private final Quantity quantity;
    private final Price price;
    private final Price discountPrice;
    private final String couponDiscountPercent;
    private final String couponId;
    private final String userCouponId;
}
