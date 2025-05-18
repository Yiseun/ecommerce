package com.ecommerce.member.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNumber {
    private final String value;

    public static PhoneNumber from(final String value){
        return new PhoneNumber(value);
    }
}
