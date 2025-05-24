package com.ecommerce.order.exception.application.domain;

import com.ecommerce.order.exception.application.ApplicationException;

public class DomainException extends ApplicationException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
