package com.ecommerce.member.domain;

import com.ecommerce.member.exception.application.domain.InvalidConstructionException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {
    private final String value;

    public static Email from(final String value){
        if(value==null){
            throw new InvalidConstructionException("email은 null일수 없습니다.");
        }
        return new Email(value);
    }
}
