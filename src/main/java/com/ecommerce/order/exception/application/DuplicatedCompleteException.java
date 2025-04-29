package com.ecommerce.order.exception.application;

public class DuplicatedCompleteException extends ApplicationException{
    public DuplicatedCompleteException(final String message) {
        super(message);
    }

    public DuplicatedCompleteException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
