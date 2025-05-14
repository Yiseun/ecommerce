package com.ecommerce.auth.exception.application;

public class AccessCodeCreationFailureException extends ApplicationException{
    public AccessCodeCreationFailureException(final String message) {
        super(message);
    }

    public AccessCodeCreationFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
