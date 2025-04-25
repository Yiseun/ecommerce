package com.ecommerce.payment.portone.exception;

public class ConnectionFailureException extends PortoneException{
    public ConnectionFailureException(final String message) {
        super(message);
    }

    public ConnectionFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
