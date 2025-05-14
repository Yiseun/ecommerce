package com.ecommerce.auth.mail.exception;

import com.ecommerce.grobal.exception.EcommerceException;

public class UnsupportedException extends EcommerceException {
    public UnsupportedException(final String message) {
        super(message);
    }

    public UnsupportedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
