package com.ecommerce.coupon.exception.application;

import com.ecommerce.coupon.exception.application.ApplicationException;

public class CouponAndProductRelationshipNotFoundException extends ApplicationException {

    public CouponAndProductRelationshipNotFoundException(final String message) {
        super(message);
    }

    public CouponAndProductRelationshipNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
