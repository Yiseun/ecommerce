package com.ecommerce.payment.portone.exception.http;

public class AlreadyPaidException extends HttpStatusException{
    public AlreadyPaidException(final String message) {
        super(message);
    }

    public AlreadyPaidException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
