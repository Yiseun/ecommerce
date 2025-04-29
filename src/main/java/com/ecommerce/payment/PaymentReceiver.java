package com.ecommerce.payment;

import com.ecommerce.payment.dto.internal.InternalPaymentPurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentReceiver {
    private final PaymentService paymentService;

    public void purchase(final InternalPaymentPurchaseRequest request){
        paymentService.purchase(request);
    }
}
