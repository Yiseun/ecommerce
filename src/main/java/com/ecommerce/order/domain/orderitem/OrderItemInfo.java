package com.ecommerce.order.domain.orderitem;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class OrderItemInfo {
    @EqualsAndHashCode.Include //이거 이렇게하는게 맞을까? completeOrder 만들면서 생각해보자
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
