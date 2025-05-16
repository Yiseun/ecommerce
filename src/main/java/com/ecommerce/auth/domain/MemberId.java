package com.ecommerce.auth.domain;

import com.ecommerce.auth.exception.application.domain.InvalidUserInputException;
import lombok.Getter;

@Getter
public class MemberId {
    private static final int MINIMUM_ID_LENGTH = 8;
    private static final int MAXIMUM_ID_LENGTH = 20;
    private final String value;
    private MemberId(final String value){
        this.value = validate(value);
    }
    private String validate(final String value){
        if(value==null){
            throw new InvalidUserInputException("memberId는 필수입력입니다.");
        }
        return value;
    }

    public static MemberId from(final String value){
        return new MemberId(value);
    }
}