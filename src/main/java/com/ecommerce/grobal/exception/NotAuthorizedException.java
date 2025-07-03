package com.ecommerce.grobal.exception;

public class NotAuthorizedException extends EcommerceException{
    public NotAuthorizedException(final String message) {
        super(message);
    }

    public NotAuthorizedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
