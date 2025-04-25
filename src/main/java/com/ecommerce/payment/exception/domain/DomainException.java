package com.ecommerce.payment.exception.domain;

import com.ecommerce.payment.exception.PaymentException;

public class DomainException extends PaymentException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
