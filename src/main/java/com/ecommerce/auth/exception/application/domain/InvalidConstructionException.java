package com.ecommerce.auth.exception.application.domain;

public class InvalidConstructionException extends DomainException{
    public InvalidConstructionException(final String message) {
        super(message);
    }

    public InvalidConstructionException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
