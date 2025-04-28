package com.ecommerce.payment.domain.session;

import com.ecommerce.payment.domain.PaymentInfo;
import com.ecommerce.payment.domain.paymentitem.Price;
import com.ecommerce.payment.domain.paymentitem.PurchaseItem;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Builder
@Getter
@RequiredArgsConstructor
public class PaymentSession {
    private final PaymentKey paymentKey;
    private final String memberId;
    private final String orderId;
    private final PaymentInfo paymentInfo;
    private final Price totalPrice;
    private final List<PurchaseItem> purchaseItems;
}