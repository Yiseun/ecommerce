package com.ecommerce.coupon.exception.application;

import com.ecommerce.coupon.exception.CouponException;

public class ApplicationException extends CouponException {
    public ApplicationException(final String message) {
        super(message);
    }

    public ApplicationException(final String message, Throwable cause) {
        super(message, cause);
    }
}
