package com.ecommerce.payment.portone.exception.http;

public class UnauthorizedException extends HttpStatusException{
    public UnauthorizedException(final String message) {
        super(message);
    }

    public UnauthorizedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
