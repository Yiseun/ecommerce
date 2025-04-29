package com.ecommerce.payment.exception.application;

public class DuplicatedPurchaseException extends ApplicationException{
    public DuplicatedPurchaseException(final String message) {
        super(message);
    }

    public DuplicatedPurchaseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
