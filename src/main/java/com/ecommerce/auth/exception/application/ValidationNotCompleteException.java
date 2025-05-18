package com.ecommerce.auth.exception.application;

public class ValidationNotCompleteException extends ApplicationException{
    public ValidationNotCompleteException(final String message) {
        super(message);
    }

    public ValidationNotCompleteException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
