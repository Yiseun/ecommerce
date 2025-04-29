package com.ecommerce.payment.exception.domain;

public class UnderstockedException extends DomainException{
    public UnderstockedException(final String message) {
        super(message);
    }

    public UnderstockedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
