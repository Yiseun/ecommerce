package com.ecommerce.coupon.exception.domain;

import com.ecommerce.coupon.exception.CouponException;

public class DomainException extends CouponException {
    public DomainException(final String message) {
        super(message);
    }

    public DomainException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
