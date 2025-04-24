package com.ecommerce.order.exception.application;

import com.ecommerce.order.exception.OrderException;

public class ApplicationException extends OrderException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
