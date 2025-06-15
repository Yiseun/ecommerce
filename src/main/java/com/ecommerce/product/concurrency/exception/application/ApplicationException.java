package com.ecommerce.product.concurrency.exception.application;

import com.ecommerce.product.concurrency.exception.ConcurrencyException;

public class ApplicationException extends ConcurrencyException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
