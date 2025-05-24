package com.ecommerce.order.exception.domain;

import com.ecommerce.order.exception.OrderException;

public class DomainException extends OrderException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
