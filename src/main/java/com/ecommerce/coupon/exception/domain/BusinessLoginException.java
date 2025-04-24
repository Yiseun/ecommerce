package com.ecommerce.coupon.exception.domain;

public class BusinessLoginException extends DomainException {
    public BusinessLoginException(final String message) {
        super(message);
    }

    public BusinessLoginException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
