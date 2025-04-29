package com.ecommerce.payment.domain.paymentitem;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentItem {
    @EqualsAndHashCode.Include
    private final String orderItemId;
    private final PaymentItemInfo paymentItemInfo;

    public static PaymentItem of(final String orderItemId,final PaymentItemInfo paymentItemInfo){
        return new PaymentItem(orderItemId, paymentItemInfo);
    }
}
