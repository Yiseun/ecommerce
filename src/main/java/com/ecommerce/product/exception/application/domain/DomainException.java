package com.ecommerce.product.exception.application.domain;

import com.ecommerce.product.exception.application.ApplicationException;

public class DomainException extends ApplicationException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
