package com.ecommerce.auth.exception.application.domain.business;

public class OutOfTryCountException extends BusinessLogicException{
    public OutOfTryCountException(final String message) {
        super(message);
    }

    public OutOfTryCountException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
