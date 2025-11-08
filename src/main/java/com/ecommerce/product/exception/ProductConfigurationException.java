package com.ecommerce.product.exception;

public class ProductConfigurationException extends ProductException{
    public ProductConfigurationException(final String message) {
        super(message);
    }

    public ProductConfigurationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
