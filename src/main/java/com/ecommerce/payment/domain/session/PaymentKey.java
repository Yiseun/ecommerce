package com.ecommerce.payment.domain.session;

import lombok.Getter;

@Getter
public class PaymentKey {
    private final String value;
    private PaymentKey(final String value){
        this.value = value;
    }
    public static PaymentKey from(final String value){
        return new PaymentKey(value);
    }
}
