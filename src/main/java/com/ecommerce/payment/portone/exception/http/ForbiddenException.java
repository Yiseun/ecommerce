package com.ecommerce.payment.portone.exception.http;

public class ForbiddenException extends HttpStatusException{
    public ForbiddenException(final String message) {
        super(message);
    }

    public ForbiddenException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
