package com.ecommerce.payment.domain.paymentitem;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
public class PaymentItemInfo {
    private final String productId;
    private final String productName;
    private final Quantity quantity;
    private final Price price;
    private final Price discountPrice;
    private final String couponDiscountPercent;
    private final String couponId;
    private final String userCouponId;
}
