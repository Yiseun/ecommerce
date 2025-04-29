package com.ecommerce.payment.dto;

import com.ecommerce.payment.domain.session.PaymentSession;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CreatePaymentSessionResponse {
    private final String paymentKey;
    private final String orderId;
    private final String pgProvider;

    public static CreatePaymentSessionResponse from(final PaymentSession paymentSession){
        return CreatePaymentSessionResponse.builder()
                .paymentKey(paymentSession.getPaymentKey().getValue())
                .orderId(paymentSession.getOrderId())
                .pgProvider(paymentSession.getPaymentInfo().getPgProvider().name())
                .build();
    }
}
