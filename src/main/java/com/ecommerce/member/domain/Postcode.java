package com.ecommerce.member.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Postcode {
    private final String value;

    public static Postcode from(final String value){
        return new Postcode(value);
    }
}
