package com.ecommerce.auth.exception.application;

public class AuthNotFoundException extends ApplicationException{
    public AuthNotFoundException(final String message) {
        super(message);
    }

    public AuthNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
