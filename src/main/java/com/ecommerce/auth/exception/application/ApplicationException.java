package com.ecommerce.auth.exception.application;

import com.ecommerce.auth.exception.AuthException;

public class ApplicationException extends AuthException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
