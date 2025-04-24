package com.ecommerce.product.exception.application;

import com.ecommerce.product.exception.ProductException;

public class ApplicationException extends ProductException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
