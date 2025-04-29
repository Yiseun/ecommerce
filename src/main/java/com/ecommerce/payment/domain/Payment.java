package com.ecommerce.payment.domain;

import com.ecommerce.payment.domain.paymentitem.PaymentItem;
import com.ecommerce.payment.domain.paymentitem.Price;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Payment {
    private final String impUid;
    private final String orderId;
    private final PaymentInfo paymentInfo;
    private final String memberId;
    private final Price totalPrice;
    private final List<PaymentItem> paymentItems;
}
