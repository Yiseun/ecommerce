package com.ecommerce.member.exception.application;

public class MemberNotFoundException extends ApplicationException{
    public MemberNotFoundException(final String message) {
        super(message);
    }

    public MemberNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
