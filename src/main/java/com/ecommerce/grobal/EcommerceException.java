package com.ecommerce.grobal;

public class EcommerceException extends RuntimeException{

    public EcommerceException(final String message){
        super(message);
    }
    public EcommerceException(final String message,final Throwable cause){
        super(message,cause);
    }

}
