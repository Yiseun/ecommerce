package com.ecommerce.order.exception.application;

public class InvalidConstructionException extends ApplicationException{
    public InvalidConstructionException(final String message) {
        super(message);
    }

    public InvalidConstructionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
