package com.ecommerce.coupon.exception.application;

public class UserCouponNotFoundException extends ApplicationException{
    public UserCouponNotFoundException(final String message) {
        super(message);
    }

    public UserCouponNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
