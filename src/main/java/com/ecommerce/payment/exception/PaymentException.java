package com.ecommerce.payment.exception;

import com.ecommerce.grobal.EcommerceException;

public class PaymentException extends EcommerceException {
    public PaymentException(final String message) {
        super(message);
    }

    public PaymentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
