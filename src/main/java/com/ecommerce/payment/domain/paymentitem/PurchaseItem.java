package com.ecommerce.payment.domain.paymentitem;

import lombok.*;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PurchaseItem {
    private final String orderItemId;
    private final PaymentItemInfo paymentItemInfo;

    public static PurchaseItem of(final String orderItemId,final PaymentItemInfo paymentItemInfo){
        return new PurchaseItem(orderItemId, paymentItemInfo);
    }
}
