package com.ecommerce.order.exception.application;

public class OrderNotFoundException extends ApplicationException{
    public OrderNotFoundException(final String message) {
        super(message);
    }

    public OrderNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
