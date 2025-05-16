package com.ecommerce.auth.exception.application;

public class DuplicatedRegistrationException extends ApplicationException{
    public DuplicatedRegistrationException(final String message) {
        super(message);
    }

    public DuplicatedRegistrationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
