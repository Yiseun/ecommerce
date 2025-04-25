package com.ecommerce.payment.portone.exception.http;

public class UndefinedException extends HttpStatusException {
    public UndefinedException(final String message) {
        super(message);
    }

    public UndefinedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
