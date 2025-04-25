package com.ecommerce.payment.portone.exception;

import com.ecommerce.payment.exception.PaymentException;

public class PortoneException extends PaymentException {
    public PortoneException(final String message) {
        super(message);
    }

    public PortoneException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
