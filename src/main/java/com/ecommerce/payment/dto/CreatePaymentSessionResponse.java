package com.ecommerce.payment.dto;

import com.ecommerce.payment.domain.session.PaymentSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePaymentSessionResponse {
    private final String paymentKey;
    private final String pgProvider;

    public static CreatePaymentSessionResponse from(final PaymentSession paymentSession){
        return new CreatePaymentSessionResponse(paymentSession.getPaymentKey().getValue(),paymentSession.getPaymentInfo().getPgProvider().name());
    }
}
