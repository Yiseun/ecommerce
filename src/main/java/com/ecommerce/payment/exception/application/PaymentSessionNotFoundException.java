package com.ecommerce.payment.exception.application;

public class PaymentSessionNotFoundException extends ApplicationException{
    public PaymentSessionNotFoundException(final String message) {
        super(message);
    }

    public PaymentSessionNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
