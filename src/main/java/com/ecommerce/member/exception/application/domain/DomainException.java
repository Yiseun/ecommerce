package com.ecommerce.member.exception.application.domain;

import com.ecommerce.member.exception.application.ApplicationException;

public class DomainException extends ApplicationException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
