package com.ecommerce.payment.portone.exception.http;

import com.ecommerce.payment.portone.exception.PortoneException;

public class HttpStatusException extends PortoneException {
    public HttpStatusException(final String message) {
        super(message);
    }

    public HttpStatusException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
