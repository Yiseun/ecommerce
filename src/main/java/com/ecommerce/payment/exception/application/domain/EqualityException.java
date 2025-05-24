package com.ecommerce.payment.exception.application.domain;

public class EqualityException extends DomainException{
    public EqualityException(final String message) {
        super(message);
    }

    public EqualityException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
