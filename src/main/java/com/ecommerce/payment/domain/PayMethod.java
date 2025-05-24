package com.ecommerce.payment.domain;

import com.ecommerce.payment.exception.application.domain.FailedCreationException;

public enum PayMethod {
    CARD,
    MOBILE,
    TRANSFER;

    public static PayMethod from(final String value){
        try {
            return PayMethod.valueOf(value);
        }catch (IllegalArgumentException | NullPointerException e){
            throw new FailedCreationException("올바르지않은 PayMethod입니다.");
        }
    }
}
