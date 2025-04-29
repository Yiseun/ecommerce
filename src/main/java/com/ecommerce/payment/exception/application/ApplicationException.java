package com.ecommerce.payment.exception.application;

import com.ecommerce.payment.exception.PaymentException;

public class ApplicationException extends PaymentException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
