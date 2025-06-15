package com.ecommerce.product.exception.application;

public class DuplicatedCreationException extends ApplicationException{
    public DuplicatedCreationException(final String message) {
        super(message);
    }

    public DuplicatedCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
