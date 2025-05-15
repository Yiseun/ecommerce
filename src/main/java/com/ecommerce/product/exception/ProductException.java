package com.ecommerce.product.exception;

import com.ecommerce.grobal.exception.EcommerceException;

public class ProductException extends EcommerceException {
    public ProductException(final String message){
        super(message);
    }
    public ProductException(final String message,final Throwable cause){
        super(message,cause);
    }
}
