package com.ecommerce.auth.domain.sessiondata.prevalidation;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class AccessCode {
    private final String value;
    private AccessCode(final String value){
        this.value = value;
    }
    public boolean isEmpty(){
        return this.value == null;
    }
    public static AccessCode from(final String randomCode){
        return new AccessCode(randomCode);
    }
}
