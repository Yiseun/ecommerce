package com.ecommerce.payment.domain.paymentitem;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PurchaseItem {
    private final String productId;
    private final String productName;
    private final Quantity quantity;
    private final Price price;
    private final String discountPrice;
    private final String couponDiscountPercent;
    private final String couponId;
    private final String userCouponId;
}
