package com.ecommerce.member.exception;

import com.ecommerce.grobal.exception.EcommerceException;

public class MemberException extends EcommerceException {
    public MemberException(final String message) {
        super(message);
    }

    public MemberException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
