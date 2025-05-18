package com.ecommerce.member.exception.application;

import com.ecommerce.member.exception.MemberException;

public class ApplicationException extends MemberException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
