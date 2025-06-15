package com.ecommerce.product.concurrency.exception;

import com.ecommerce.product.exception.ProductException;

public class ConcurrencyException extends ProductException {
    public ConcurrencyException(final String message) {
        super(message);
    }

    public ConcurrencyException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
