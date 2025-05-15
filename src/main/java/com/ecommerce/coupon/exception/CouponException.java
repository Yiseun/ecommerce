package com.ecommerce.coupon.exception;

import com.ecommerce.grobal.exception.EcommerceException;

public class CouponException extends EcommerceException {
    public CouponException(final String message) {
        super(message);
    }
    public CouponException(final String message,final Throwable cause){
        super(message, cause);
    }
}
