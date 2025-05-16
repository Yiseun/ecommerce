package com.ecommerce.auth.exception.application;

public class PrevalidationNotCompleteException extends ApplicationException{
    public PrevalidationNotCompleteException(final String message) {
        super(message);
    }

    public PrevalidationNotCompleteException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
