package com.ecommerce.payment.portone.exception.http;

public class InvalidRequestException extends HttpStatusException{
    public InvalidRequestException(final String message) {
        super(message);
    }

    public InvalidRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
