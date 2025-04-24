package com.ecommerce.coupon.exception.domain;

public class BusinessLogicException extends DomainException {
    public BusinessLogicException(final String message) {
        super(message);
    }

    public BusinessLogicException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
