package com.ecommerce.product.concurrency.exception;

public class PropertyBindingException extends ConcurrencyException{
    public PropertyBindingException(final String message) {
        super(message);
    }

    public PropertyBindingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
