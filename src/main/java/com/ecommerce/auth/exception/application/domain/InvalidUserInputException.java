package com.ecommerce.auth.exception.application.domain;

public class InvalidUserInputException extends DomainException{
    public InvalidUserInputException(final String message) {
        super(message);
    }

    public InvalidUserInputException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
