package com.ecommerce.coupon.exception.application;

public class CouponInvalidException extends ApplicationException{
    public CouponInvalidException(final String message) {
        super(message);
    }

    public CouponInvalidException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
