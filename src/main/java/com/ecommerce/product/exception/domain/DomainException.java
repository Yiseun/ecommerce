package com.ecommerce.product.exception.domain;

import com.ecommerce.product.exception.ProductException;

public class DomainException extends ProductException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
