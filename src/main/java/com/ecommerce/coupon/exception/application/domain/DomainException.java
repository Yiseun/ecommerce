package com.ecommerce.coupon.exception.application.domain;

import com.ecommerce.coupon.exception.application.ApplicationException;

public class DomainException extends ApplicationException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
