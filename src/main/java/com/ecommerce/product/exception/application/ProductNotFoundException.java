package com.ecommerce.product.exception.application;

public class ProductNotFoundException extends ApplicationException{

    public ProductNotFoundException(final String message) {
        super(message);
    }

    public ProductNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
