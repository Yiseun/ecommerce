package com.ecommerce.auth.exception;

import com.ecommerce.grobal.exception.EcommerceException;

public class AuthException extends EcommerceException {
    public AuthException(final String message) {
        super(message);
    }

    public AuthException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
