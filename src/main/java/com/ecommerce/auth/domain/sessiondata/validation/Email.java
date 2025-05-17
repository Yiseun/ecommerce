package com.ecommerce.auth.domain.sessiondata.validation;

import lombok.Getter;

@Getter
public class Email {
    private final String value;
    private Email(final String value){
        this.value = value;
    }
    public boolean isEmpty(){
        return value == null;
    }
    public static Email from(final String value){
        return new Email(value);
    }
}
