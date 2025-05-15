package com.ecommerce.auth.exception.application.domain.business;

import com.ecommerce.auth.exception.application.domain.DomainException;

public class BusinessLogicException extends DomainException {
    public BusinessLogicException(final String message) {
        super(message);
    }

    public BusinessLogicException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
