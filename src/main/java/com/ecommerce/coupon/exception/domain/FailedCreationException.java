package com.ecommerce.coupon.exception.domain;

import com.ecommerce.coupon.exception.domain.DomainException;

public class FailedCreationException extends DomainException {
    public FailedCreationException(final String message) {
        super(message);
    }

    public FailedCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
