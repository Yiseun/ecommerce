package com.ecommerce.payment.portone.exception;

public class TypeCastException extends PortoneException {
    public TypeCastException(final String message) {
        super(message);
    }

    public TypeCastException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
