package com.ecommerce.auth.exception.application;

public class MailSendFailureException extends ApplicationException{
    public MailSendFailureException(final String message) {
        super(message);
    }

    public MailSendFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
