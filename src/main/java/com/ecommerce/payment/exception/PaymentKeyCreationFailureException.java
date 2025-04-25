package com.ecommerce.payment.exception;

public class PaymentKeyCreationFailureException extends PaymentException{
    public PaymentKeyCreationFailureException(final String message) {
        super(message);
    }

    public PaymentKeyCreationFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
