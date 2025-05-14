package com.ecommerce.grobal.exception;

public class InvalidTimezoneException extends EcommerceException{
    public InvalidTimezoneException(final String message) {
        super(message);
    }

    public InvalidTimezoneException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
