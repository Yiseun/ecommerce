package com.ecommerce.order.exception;

import com.ecommerce.grobal.exception.EcommerceException;

public class OrderException extends EcommerceException {
    public OrderException(final String message) {
        super(message);
    }
    public OrderException(final String message,final Throwable cause){
        super(message, cause);
    }
}
