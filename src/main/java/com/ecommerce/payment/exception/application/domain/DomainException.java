package com.ecommerce.payment.exception.application.domain;

import com.ecommerce.payment.exception.application.ApplicationException;

public class DomainException extends ApplicationException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
