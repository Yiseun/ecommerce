package com.ecommerce.order.domain;

import com.ecommerce.order.domain.orderitem.OrderItemId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class TmpOrderItem {
    private OrderItemId orderItemId;
    private String productId;
    private String productName;
    private String quantity;
    private String price;
    private String discountPrice;
    private String couponDiscountPercent;
    private String couponId;
    private String userCouponId;
}
